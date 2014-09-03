package com.ninjamind.conference.repository.interetdbsetup;

import com.ninjamind.conference.config.ApplicationConfig;
import com.ninjamind.conference.repository.interetdbsetup.sav.AbstractDbunitRepositoryTest;
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
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Classe parente de toutes nos classes de tests permettant de charger le contexte Spring des tests propres
 * au repository
 * @author ehret_g
 */
public class DbunitTestRule extends ExternalResource {

    private static Properties properties = new Properties();
    protected IDatabaseTester databaseTester;
    protected static String databaseJdbcDriver;
    protected static String databaseUrl;
    protected static String databaseUsername;
    protected static String databasePassword;
    protected IDataSet dataSet;

    public DbunitTestRule(IDataSet dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    protected void before() throws Throwable {
        super.before();
        initProperties();
        databaseTester = new JdbcDatabaseTester(databaseJdbcDriver, databaseUrl, databaseUsername, databasePassword);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    public static void initProperties() throws IOException {
        if(databaseJdbcDriver==null) {
            properties.load(AbstractDbunitRepositoryTest.class.getResourceAsStream("/application.properties"));
            databaseJdbcDriver = properties.getProperty("db.driver");
            databaseUrl = properties.getProperty("db.url");
            databaseUsername = properties.getProperty("db.username");
            databasePassword = properties.getProperty("db.password");
        }
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
