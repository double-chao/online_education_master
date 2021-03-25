package com.lcc.eduservice.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.github.pagehelper.PageInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@MapperScan("com.lcc.eduservice.mapper")
public class EduConfig {

    /**
     * 逻辑删除插件
     */
    @Bean
    public ISqlInjector sqlInjector() { // ！！！注意： 从3.1.1开始 不在需要这一步了
        return new LogicSqlInjector();
    }

    /**
     * mybatis-plus分页插件配置
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * PageHelper分页插件配置
     * （引用<groupId>com.github.pagehelper</groupId><artifactId>pagehelper</artifactId> 需要配置的）
     * 引用groupId>com.github.pagehelper</groupId><artifactId>pagehelper-spring-boot-starter</artifactId> 不需要再注入这个配置了
     */
    @Bean
    PageInterceptor pageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        pageInterceptor.setProperties(properties);  // 由此可进入源码，
        return pageInterceptor;
    }

}
