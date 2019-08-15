package com.mmall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;


import java.util.ArrayList;
import java.util.List;
import java.nio.charset.Charset;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends AsyncConfigurerSupport {

    @Bean(name = "stringHttpMessageConverter")
    public StringHttpMessageConverter initStringHttpMessageConverter() {
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return stringHttpMessageConverter;
    }

    /**
     * 初始化RequestMappingHandlerAdapter，并加载Http的Json转换器
     * @return  RequestMappingHandlerAdapter 对象
     */
    @Bean(name="requestMappingHandlerAdapter")
    public HandlerAdapter initRequestMappingHandlerAdapter() {
        //创建RequestMappingHandlerAdapter适配器
        RequestMappingHandlerAdapter rmhd = new RequestMappingHandlerAdapter();
        //HTTP JSON转换器
        MappingJackson2HttpMessageConverter  jsonConverter
                = new MappingJackson2HttpMessageConverter();
        //MappingJackson2HttpMessageConverter接收JSON类型消息的转换
        MediaType mediaType = MediaType.APPLICATION_JSON_UTF8;
        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(mediaType);
        //加入转换器的支持类型
        jsonConverter.setSupportedMediaTypes(mediaTypes);
        //往适配器加入json转换器
        rmhd.getMessageConverters().add(jsonConverter);
        return rmhd;
    }

    //文件上传
    @Bean(name = "multipartResolver")
    public MultipartResolver initMultipartResolver() {
        long maxUploadSize = 10485760;
        int maxInMemorySize = 4096;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(maxUploadSize);
        multipartResolver.setMaxInMemorySize(maxInMemorySize);
        multipartResolver.setDefaultEncoding("UTF-8");
        return multipartResolver;
    }

}
