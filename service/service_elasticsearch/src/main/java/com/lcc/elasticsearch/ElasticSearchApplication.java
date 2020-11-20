package com.lcc.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: 一十六
 * @Description: 全文搜索引擎
 * @Date: 2020/6/16 22:17
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ElasticSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchApplication.class, args);
    }
}
