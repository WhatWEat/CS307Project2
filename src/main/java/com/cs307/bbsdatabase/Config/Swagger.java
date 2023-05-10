package com.cs307.bbsdatabase.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class Swagger {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
            .apiInfo(apiInfo())
            .enable(true)
            .groupName("CS307")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.cs307.bbsdatabase"))
            .paths(PathSelectors.any())
            .build();
    }


    @SuppressWarnings("all")
    public ApiInfo apiInfo() {
        return new ApiInfo(
            "CS307 api",
            "redis project",
            "v1.0",
            "https://github.com/WhatWEat/CS307Project2",
            "ZRJ",
            "Apache 2.0",  //许可证
            "http://www.apache.org/licenses/LICENSE-2.0" //许可证链接
        );
    }
}
