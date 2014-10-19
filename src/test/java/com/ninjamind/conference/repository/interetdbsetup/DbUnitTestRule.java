package com.ninjamind.conference.repository.interetdbsetup;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.rules.ExternalResource;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * {@link }
 *
 * @author EHRET_G
 */
public class DbUnitTestRule extends ExternalResource {
    private Properties properties = new Properties();
    protected IDatabaseTester databaseTester;
    protected IDataSet dataSet;
    protected String databaseJdbcDriver;
    protected String databaseUrl;
    protected String databaseUsername;
    protected String databasePassword;

    public DbUnitTestRule(IDataSet dataSet) {
        this.dataSet = dataSet;
    }


    @Override
    protected void before() throws Throwable {
        initProperties();
        databaseTester = new JdbcDatabaseTester(databaseJdbcDriver, databaseUrl, databaseUsername, databasePassword);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }



    private void initProperties() throws IOException {
        if (databaseJdbcDriver == null) {
            properties.load(getClass().getResourceAsStream("/application.properties"));
            databaseJdbcDriver = properties.getProperty("db.driver");
            databaseUrl = properties.getProperty("db.url");
            databaseUsername = properties.getProperty("db.username");
            databasePassword = properties.getProperty("db.password");
        }
    }


}
