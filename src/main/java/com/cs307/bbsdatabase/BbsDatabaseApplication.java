package com.cs307.bbsdatabase;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@MapperScan("com.cs307.bbsdatabase.Mapper")
public class BbsDatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BbsDatabaseApplication.class, args);
    }

}
