package com.lcc.eduorder.client.impl;

import com.lcc.eduorder.client.CourseClient;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.vo.CourseOrder;
import org.springframework.stereotype.Component;

/**
 * @Author: 一十六
 * @Description: 服务熔断后，执行的方法
 * @Date: 2020/5/30 17:37
 */
@Component
public class CourseInfoDegradeFeignClient implements CourseClient {

    @Override
    public CourseOrder getOrderCourseInfo(String id) {
        throw new BadException(20001,"获取课程信息失败");
    }
}
