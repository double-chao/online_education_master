package com.lcc.eduorder.client.impl;

import com.lcc.eduorder.client.CourseClient;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.servicebase.exceptionhandler.CodeEnum;
import com.lcc.vo.CourseOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: 一十六
 * @Description: 服务熔断后，执行的方法
 * @Date: 2020/5/30 17:37
 */
@Slf4j
@Component
public class CourseInfoDegradeFeignClient implements CourseClient {

    @Override
    public CourseOrder getOrderCourseInfo(Integer id) {
        log.error("远程调用课程服务，执行了熔断器，获取课程信息失败");
        throw new BadException(CodeEnum.COURSE_INFO_NOT_EXITS);
    }
}
