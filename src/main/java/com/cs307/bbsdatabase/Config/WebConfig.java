package com.cs307.bbsdatabase.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).excludePathPatterns("/swagger**/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //CorsRegistration addMapping(String pathPattern): 添加路径映射，如 /admin/info，或者 /admin/**
        registry.addMapping("/**")
            //是否发送Cookie
            .allowCredentials(true)
            //放行哪些原始域, * 表示所有
            .allowedOrigins("http://localhost:8080")
            //放行哪些请求方式
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            //放行哪些原始请求头部信息
            .allowedHeaders("*")
            .maxAge(16800);
    }
}
