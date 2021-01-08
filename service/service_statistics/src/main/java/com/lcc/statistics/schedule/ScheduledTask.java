package com.lcc.statistics.schedule;

import com.lcc.statistics.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * 定时器
 */
@Component
@EnableAsync // 开启异步任务功能
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService staService;

    /**
     * 在每天凌晨1点，把前一天数据进行数据查询添加
     *
     * 1、spring中，不允许第七位年的出现
     * 2、在周几的位置，1-7代表周一到周日 MON-SUN
     * 3、定时任务不应该阻塞，默认为阻塞的
     *      3.1、可以让业务以异步的方式执行，自己提交线程池，可以使用异步编排的方式
     *      3.2、支持定时任务线程池的方式执行；可能不会起作用
     *      3.3、让定时任务异步执行，  第一步：类上注解 @EnableAsync // 开启异步任务功能
     *                             第二步：方法上注解 @Async
     */
    @Async
    @Scheduled(cron = "0 0 1 * * ?") //七子表达式/Cron表达式
    public void task2() {
//        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
        String yesterday = LocalDate.now().plusDays(-1).toString();
        staService.registerCount(yesterday);
    }
}
