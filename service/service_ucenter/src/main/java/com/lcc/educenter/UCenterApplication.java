package com.lcc.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: 一十六
 * @Description:
 * @Date: 2020/5/31
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.lcc")
@MapperScan("com.lcc.educenter.mapper")
public class UCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UCenterApplication.class,args);
    }
}
