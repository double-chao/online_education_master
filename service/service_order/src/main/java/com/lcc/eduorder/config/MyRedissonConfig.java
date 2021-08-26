package com.lcc.eduorder.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @Author Administrator
 * @Description Redisson配置类
 * @Date 2020/8/31
 */
@Configuration
public class MyRedissonConfig {

    @Bean(destroyMethod="shutdown")
    public RedissonClient redisson() throws IOException {
        // 单Redis节点模式  前缀使用redis://   若是使用SSL方式，则使用rediss:// 连接
        // 1、创建配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.233.129:6379");

        // 这是设置集群模式
//        config.useClusterServers().addNodeAddress(""redis://127.0.0.1:7001",""redis://127.0.0.1:7002");

        // 2、根据config创建RedissonClient示例
        return Redisson.create(config);
    }
}
