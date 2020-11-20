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
     *
     * @param day
     */
    void registerCount(String day);

    /**
     *
     * @param type
     * @param begin
     * @param end
     * @return
     */
    Map<String, Object> getShowData(String type, String begin, String end);
}
