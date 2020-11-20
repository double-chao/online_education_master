package com.lcc.eduservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: 一十六
 * @Description: 轮播图启动类
 * @Date: 2020/5/30 21:55
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.lcc")
@MapperScan("com.lcc.eduservice.mapper")
@EnableDiscoveryClient
//@EnableCaching //spring cache 缓存注解
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class,args);
    }
}
