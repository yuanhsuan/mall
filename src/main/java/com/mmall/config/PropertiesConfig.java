package com.mmall.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

@Configuration
@PropertySource(value = {"classpath: datasource.properties"})
@Component
public class PropertiesConfig {

    @Value("${db.driverClassName}")
    private String driver = null;

    @Value("${db.url}")
    private String url = null;

    @Value("${db.username}")
    private String username = null;

    @Value("${db.password}")
    private String password = null;

    @Value("${db.maxActive}")
    private String maxActive = null;

    @Value("${db.maxIdle}")
    private String maxIdle = null;

    @Value("${db.minIdle}")
    private String minIdle = null;

    @Value("${db.maxWait}")
    private String maxWait = null;

    @Value("${db.defaultAutoCommit}")
    private String defaultAutoCommit = null;

    @Value("${db.minEvictableIdleTimeMillis}")
    private String minEvictableIdleTimeMillis = null;

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMaxActive() {
        return maxActive;
    }

    public String getMaxIdle() {
        return maxIdle;
    }

    public String getMinIdle() {
        return minIdle;
    }

    public String getMaxWait() {
        return maxWait;
    }

    public String getDefaultAutoCommit() {
        return defaultAutoCommit;
    }

    public String getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
