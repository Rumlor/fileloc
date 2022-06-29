package com.fileloc.application.appconfig;

import com.fileloc.application.appconfig.propertysource.propertysourcebinders.DataSourcePropertyBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.fileloc.application.apprepo",
        entityManagerFactoryRef = "entityManagerFactoryRef")
@EnableTransactionManagement

public class ApplicationDataSourceConfig {

    @Autowired
    private DataSourcePropertyBinder propertyBinder;

    @Bean
    public DataSource dataSourceBean(){
        final DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setDriverClassName(propertyBinder.getDriver());
        dataSource.setPassword(propertyBinder.getPassword());
        dataSource.setUsername(propertyBinder.getUsername());
        dataSource.setUrl(propertyBinder.getUrl());
        return dataSource;
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryRef(DataSource dataSourceBean){
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSourceBean);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("com.fileloc.application.domain.content");
        entityManagerFactoryBean.setJpaProperties(createJpaProperties());
        return entityManagerFactoryBean;
    }
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactoryRef){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryRef);
        return transactionManager;
    }
    private Properties createJpaProperties(){
        Properties jpaProperties=new Properties();
        jpaProperties.put("hibernate.dialect", propertyBinder.getDatabaseplatform());
        jpaProperties.put("hibernate.hbm2ddl.auto", propertyBinder.getDdlauto());
        jpaProperties.put("hibernate.show_sql",propertyBinder.getShowsql());
        return jpaProperties;
    }



}