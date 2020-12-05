package com.lcc.eduorder.client;

import com.lcc.eduorder.client.impl.CourseInfoDegradeFeignClient;
import com.lcc.vo.CourseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author: 一十六
 * @Description: ${description}
 * @Date: 2020/6/2 17:11
 */
@FeignClient(name = "service-education", fallback = CourseInfoDegradeFeignClient.class)
@Component
public interface CourseClient {

    /**
     * 根据课程id查询-订单的课程信息
     * @param id
     * @return
     */
    @PostMapping("/eduservice/coursefront/getOrderCourseInfo/{id}")
    CourseOrder getOrderCourseInfo(@PathVariable("id") Integer id);
}
