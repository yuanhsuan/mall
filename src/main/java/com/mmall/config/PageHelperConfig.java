package com.mmall.config;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Configuration
@Component
public class PageHelperConfig {
    PageInterceptor pageHelper = new PageInterceptor();
    public PageInterceptor getPageHelper(){
        Properties properties = new Properties();
        properties.setProperty("helperDialect","mysql");
        properties.setProperty("resonable","true");
        properties.setProperty("supportMethodsArguments","true");
        properties.setProperty("params","count=countSql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
