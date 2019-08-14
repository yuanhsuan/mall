package com.mmall.config;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.framework.autoproxy.AutoProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.mmall.*")
@MapperScan("com.mmall.dao")
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class RootConfig implements TransactionManagementConfigurer{

    @Autowired
    private PropertiesConfig propertiesConfig;
    private PageHelperConfig pageHelperConfig;

    @Bean(name = "dataSource")
    public DataSource initDataSource() {
        Properties props = new Properties();
        props.setProperty("DriverClassName", propertiesConfig.getDriver());
        props.setProperty("url", propertiesConfig.getUrl());
        props.setProperty("username", propertiesConfig.getUsername());
        props.setProperty("password", propertiesConfig.getPassword());
        props.setProperty("maxActive", propertiesConfig.getMaxActive());
        props.setProperty("maxIdle", propertiesConfig.getMaxIdle());
        props.setProperty("minIdle", propertiesConfig.getMinIdle());
        props.setProperty("maxWait", propertiesConfig.getMaxWait());
        props.setProperty("defaultAutoCommit", propertiesConfig.getDefaultAutoCommit());
        props.setProperty("minEvictableIdleTimeMills", propertiesConfig.getMinEvictableIdleTimeMillis());

        DataSource dataSource = null;
        try {
            if (dataSource != null) {
                return dataSource;
            }
            dataSource = BasicDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }


    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean initSqlSessionFactory(@Autowired DataSource dataSource) throws IOException {
        Interceptor interceptor = pageHelperConfig.getPageHelper();
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactory.setMapperLocations(resolver.getResources("classpath*:mappers/*Mapper.xml"));
        sqlSessionFactory.setPlugins(new Interceptor[]{interceptor});
        return sqlSessionFactory;
    }

    @Bean(name = "annotationDrivenTransactionManager")
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager =
                new DataSourceTransactionManager(initDataSource());
        return dataSourceTransactionManager;

    }


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        return propertySourcesPlaceholderConfigurer;
    }


}
