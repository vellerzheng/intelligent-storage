package com.mcloud.dbprovider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableAutoConfiguration
@EnableRedisHttpSession
@EnableEurekaClient
@ComponentScan(basePackages = "com.mcloud.dbprovider")
@MapperScan("com.mcloud.dbprovider.repository.mapper")
@SpringBootApplication
public class DbProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbProviderApplication.class, args);
	}
}
