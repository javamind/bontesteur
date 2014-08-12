package com.ninjamind.conference.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.ninjamind.conference.repository", "com.ninjamind.conference.service"})
@PropertySource("classpath:application.properties")
@EnableJpaRepositories("com.ninjamind.conference.repository")
public class ApplicationConfig {

    public static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
    public static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
    public static final String PROPERTY_NAME_DATABASE_URL = "db.url";
    public static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
    public static final String PROPERTY_NAME_DATABASE_SUPRESSCLOSE = "db.supressclose";
    public static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    public static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    public static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "hibernate.package_toscan";

    @Resource
    private Environment env;

    @Bean
    public DataSource dataSource() {
        SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
        dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
        dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
        dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
        dataSource.setSuppressClose(new Boolean(env.getRequiredProperty(PROPERTY_NAME_DATABASE_SUPRESSCLOSE)));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));

        Properties properties = new Properties();
        properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
        entityManagerFactoryBean.setJpaProperties(properties);

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

}
