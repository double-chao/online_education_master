package com.lcc.online.recruitment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author Administrator
 * @Description 在线简历启动类
 * @Date 2021/5/23  12:07
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = "com.lcc")
public class OrApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrApplication.class, args);
    }
}
