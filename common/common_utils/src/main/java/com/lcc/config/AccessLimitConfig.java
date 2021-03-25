package com.lcc.config;

import com.lcc.interceptor.AccessLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author Administrator
 * @Description
 * @Date 2021/2/19  17:48
 */
@Configuration
public class AccessLimitConfig implements WebMvcConfigurer{

//    @Bean
//    public AccessLimitInterceptor accessLimitInterceptor(){
//        return new AccessLimitInterceptor();
//    }

    @Autowired
    AccessLimitInterceptor accessLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截
        registry.addInterceptor(accessLimitInterceptor).addPathPatterns("/**").excludePathPatterns("/static/**","/login.html");
    }
}
