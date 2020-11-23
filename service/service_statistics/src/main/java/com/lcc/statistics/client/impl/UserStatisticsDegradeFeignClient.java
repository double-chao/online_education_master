package com.lcc.statistics.client.impl;

import com.lcc.result.Result;
import com.lcc.statistics.client.UserStatisticsClient;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: 一十六
 * @Description: ${description}
 * @Date: 2020/6/3 13:18
 */
@Slf4j
public class UserStatisticsDegradeFeignClient implements UserStatisticsClient {
    @Override
    public Result countRegister(String day) {
        log.error("远程调用用户服务，执行了熔断器，获取用户统计数据失败");
        return Result.fail().message("获取用户统计数据失败");
    }
}
