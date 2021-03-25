package com.lcc.eduservice.config;

import com.lcc.eduservice.util.ThreadPoolConstantUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author Administrator
 * @Description 线程池配置
 * @Date 2020/10/9
 */
@Configuration
public class MyThreadPoolConfig {
    @Bean
    public ThreadPoolExecutor threadPoolExecutor(ThreadPoolConstantUtils pool) {
        return new ThreadPoolExecutor(
                pool.CORE_SIZE,
                pool.MAX_SIZE,
                pool.KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

//    @Bean
//    public ThreadPoolExecutor threadPoolExecutor() {
//        return new ThreadPoolExecutor(
//                ThreadPoolConstantUtils.CORE_SIZE,
//                ThreadPoolConstantUtils.MAX_SIZE,
//                ThreadPoolConstantUtils.KEEP_ALIVE_TIME,
//                TimeUnit.SECONDS,
//                new LinkedBlockingDeque<>(10000),
//                Executors.defaultThreadFactory(),
//                new ThreadPoolExecutor.CallerRunsPolicy()
//        );
//    }
}
