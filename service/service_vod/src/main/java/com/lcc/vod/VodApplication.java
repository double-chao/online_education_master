package com.lcc.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)  //这个注解的含义是，不去加载数据配置文件的信息
@EnableDiscoveryClient  // 在nocas注册服务
@ComponentScan(basePackages = {"com.lcc"})
public class VodApplication {

    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class, args);
    }

}
