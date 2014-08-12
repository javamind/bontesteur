package com.ninjamind.conference.repository.performance;

import com.ninjamind.conference.config.ApplicationConfig;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.BeforeClass;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.util.Properties;

/**
 * Classe parente de toutes nos classes de tests permettant de charger le contexte Spring des tests propres
 * au repository
 * @author ehret_g
 */
@ContextConfiguration(classes = {ApplicationConfig.class})
public abstract class AbstractDbunitTestNgRepositoryTest extends AbstractTransactionalTestNGSpringContextTests{

    private static Properties properties = new Properties();
    protected IDatabaseTester databaseTester;
    protected static String databaseJdbcDriver;
    protected static String databaseUrl;
    protected static String databaseUsername;
    protected static String databasePassword;


    @BeforeClass
    public static void initProperties() throws IOException {
        if(databaseJdbcDriver==null) {
            properties.load(AbstractDbunitTestNgRepositoryTest.class.getResourceAsStream("/application.properties"));
            databaseJdbcDriver = properties.getProperty("db.driver");
            databaseUrl = properties.getProperty("db.url");
            databaseUsername = properties.getProperty("db.username");
            databasePassword = properties.getProperty("db.password");
        }
    }

    @BeforeMethod
    public void importDataSet() throws Exception {
        initProperties();
        IDataSet dataSet = readDataSet();
        databaseTester = new JdbcDatabaseTester(databaseJdbcDriver, databaseUrl, databaseUsername, databasePassword);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    protected abstract IDataSet readDataSet() throws Exception;

}
