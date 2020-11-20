package com.lcc.statistics.controller;


import com.lcc.result.Result;
import com.lcc.statistics.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author chaochao
 * @since 2020-06-03
 */
@Api(description = "统计")
@RestController
@RequestMapping("/statistics/daily")
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsService;

    @ApiOperation("统计某天注册人数")
    @PostMapping("/registerCount/{day}")
    public Result registerCount(@PathVariable String day) {
        statisticsService.registerCount(day);
        return Result.ok();
    }

    @ApiOperation("展示统计数据")
    @GetMapping("/showData/{type}/{begin}/{end}")
    public Result showData(@PathVariable String type,@PathVariable String begin,
                      @PathVariable String end) {
        Map<String,Object> map = statisticsService.getShowData(type,begin,end);
        return Result.ok().data(map);
    }


}

