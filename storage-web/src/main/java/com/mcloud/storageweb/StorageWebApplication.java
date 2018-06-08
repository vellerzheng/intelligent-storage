package com.mcloud.storageweb;

import io.swagger.annotations.SwaggerDefinition;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EnableCaching
@EnableEurekaClient
@ComponentScan(basePackages = "com.mcloud.storageweb")
@MapperScan("com.mcloud.storageweb.repository.mapper")
@SpringBootApplication
public class StorageWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageWebApplication.class, args);
    }
}
