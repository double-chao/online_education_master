package com.lcc.oaservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author Administrator
 * @Description
 * @Date 2021/1/13  11:09
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.lcc")
@MapperScan("com.lcc.oaservice.mapper")
public class OaApplication {
    public static void main(String[] args) {
        SpringApplication.run(OaApplication.class, args);
    }
}
