package com.lcc.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcc.result.Result;
import com.lcc.statistics.client.UserStatisticsClient;
import com.lcc.statistics.entity.StatisticsDaily;
import com.lcc.statistics.mapper.StatisticsDailyMapper;
import com.lcc.statistics.service.StatisticsDailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author chaochao
 * @since 2020-06-03
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UserStatisticsClient userClient;

    @Override
    public void registerCount(String day) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper); //先删除当天数据，然后再添加

        Result result = userClient.countRegister(day);
        Integer countRegister = (Integer) result.getData().get("countRegister");

        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(countRegister); //
        sta.setDateCalculated(day);//

        // TODO 暂为随机数，后续完善
        sta.setVideoViewNum(RandomUtils.nextInt(100, 200));
        sta.setLoginNum(RandomUtils.nextInt(100, 200));
        sta.setCourseNum(RandomUtils.nextInt(100, 200));

        baseMapper.insert(sta);
    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.select("date_calculated", type);
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);

        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();

        staList.forEach(daily -> {
            date_calculatedList.add(daily.getDateCalculated());
            switch (type) {
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        });
//        for (StatisticsDaily daily : staList) {
//            date_calculatedList.add(daily.getDateCalculated());
//            switch (type) {
//                case "login_num":
//                    numDataList.add(daily.getLoginNum());
//                    break;
//                case "register_num":
//                    numDataList.add(daily.getRegisterNum());
//                    break;
//                case "video_view_num":
//                    numDataList.add(daily.getVideoViewNum());
//                    break;
//                case "course_num":
//                    numDataList.add(daily.getCourseNum());
//                    break;
//                default:
//                    break;
//            }
//        }
        Map<String, Object> map = new HashMap<>(date_calculatedList.size() + numDataList.size());
        map.put("date_calculatedList", date_calculatedList);
        map.put("numDataList", numDataList);
        return map;
    }

}
