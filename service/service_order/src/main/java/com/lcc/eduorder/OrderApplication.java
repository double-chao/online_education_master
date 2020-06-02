package com.lcc.eduorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @Author: 一十六
 * @Description: 订单
 * @Date: 2020/6/2 16:13
 */
@SpringBootApplication
@ComponentScan("com.lcc")
@MapperScan("com.lcc.eduorder.mapper")
@EnableDiscoveryClient
@EnableFeignClients
@CrossOrigin
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
