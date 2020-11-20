package com.lcc.statistics.client;

import com.lcc.result.Result;
import com.lcc.statistics.client.impl.UserStatisticsDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: 一十六
 * @Description: ${description}
 * @Date: 2020/6/3 13:15
 */
@Component
@FeignClient(name = "service-ucenter",fallback = UserStatisticsDegradeFeignClient.class)
public interface UserStatisticsClient {

    @GetMapping("/educenter/member/countRegister/{day}")
    Result countRegister(@PathVariable("day") String day);
}
