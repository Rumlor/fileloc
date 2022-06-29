package com.fileloc.application.appconfig.propertysource.propertysourcebinders;

import com.fileloc.application.appconfig.propertysource.DataSourceProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "app.data")
@PropertySource(value ="classpath:datasourceconfig.yml" ,factory = DataSourceProperty.class)
@Getter
@Setter
public class DataSourcePropertyBinder {


    private String driver;
    private String url;
    private String username;
    private String password;
    private String generateddl;
    private String showsql;
    private String databaseplatform;
    private String ddlauto;
}
