package com.lcc.statistics.client.impl;

import com.lcc.result.Result;
import com.lcc.statistics.client.UserStatisticsClient;

/**
 * @Author: 一十六
 * @Description: ${description}
 * @Date: 2020/6/3 13:18
 */
public class UserStatisticsDegradeFeignClient implements UserStatisticsClient {
    @Override
    public Result countRegister(String day) {
        return Result.fail().message("获取用户统计数据失败");
    }
}
