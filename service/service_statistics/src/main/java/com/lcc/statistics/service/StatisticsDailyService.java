package com.lcc.statistics.service;

import com.lcc.statistics.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author chaochao
 * @since 2020-06-03
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    /**
     * 某一天的注册人数
     * @param day 天
     */
    void registerCount(String day);

    /**
     * 获取统计数据
     * @param type 统计数据的类型
     * @param begin 开始时间
     * @param end 结束时间
     * @return 返回值
     */
    Map<String, Object> getShowData(String type, String begin, String end);
}
