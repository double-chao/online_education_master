package com.lcc.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: 一十六
 * @Description: 全文搜索引擎
 * @Date: 2020/6/16 22:17
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients  // 调用服务，因为这里要调用注册中心的服务，所以要加这个注解
@ComponentScan(basePackages = "com.lcc")  //组件扫描  扫描这个包下的所有配置，若不配置，只能扫描该模块下的配置类，其他模块中就算引入了依赖，也不能被扫描到
public class ElasticSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchApplication.class, args);
    }
}
