package com.lcc.eduservice.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author Administrator
 * @Description 线程池常量
 * @Date 2020/10/9  13:57
 */
@Component
public class ThreadPoolConstantUtils implements InitializingBean {

    @Value("${education.thread.pool.core_size}")
    private int coreSize;

    @Value("${education.thread.pool.max_size}")
    private int maxSize;

    @Value("${education.thread.pool.keep_alive_time}")
    private long keepAliveTime;

    public static int CORE_SIZE;
    public static int MAX_SIZE;
    public static long KEEP_ALIVE_TIME;

    @Override
    public void afterPropertiesSet() throws Exception {
        CORE_SIZE = coreSize;
        MAX_SIZE = maxSize;
        KEEP_ALIVE_TIME = keepAliveTime;
    }

}
