package com.ninjamind.conference.repository.interetdbsetup;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Classe parente de toutes nos classes de tests permettant de charger le contexte Spring des tests propres
 * au repository
 * @author ehret_g
 */
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractDbunitRepositoryTest {

    private static Properties properties = new Properties();
    protected IDatabaseTester databaseTester;
    protected static String databaseJdbcDriver;
    protected static String databaseUrl;
    protected static String databaseUsername;
    protected static String databasePassword;
    protected IDataSet dataSet;



    protected abstract IDataSet readDataSet();

    @BeforeClass
    public static void initProperties() throws IOException {
        if(databaseJdbcDriver==null) {
            properties.load(AbstractDbunitRepositoryTest.class.getResourceAsStream("/application.properties"));
            databaseJdbcDriver = properties.getProperty("db.driver");
            databaseUrl = properties.getProperty("db.url");
            databaseUsername = properties.getProperty("db.username");
            databasePassword = properties.getProperty("db.password");
        }
    }

    @Before
    public void importDataSet() throws Exception {
        initProperties();
        IDataSet dataSet = readDataSet();
        databaseTester = new JdbcDatabaseTester(databaseJdbcDriver, databaseUrl, databaseUsername, databasePassword);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }



    public void assertTableInDatabaseIsEqualToXmlDataset(String tableName, String pathDataSetExpected, String ... includedColumns){
        try {
            IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable(tableName);
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File(pathDataSetExpected));
            ITable expectedTable = expectedDataSet.getTable(tableName);
            ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable, includedColumns);
            Assertion.assertEquals(expectedTable, filteredActualTable);

        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }







}
