package com.mcloud.filelocalbk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EnableEurekaClient
@SpringBootApplication
public class FileLocalbkApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileLocalbkApplication.class, args);
    }
}
