package com.lcc.eduservice.service.front;

import com.lcc.result.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Administrator
 * @Description 前台课程接口信息
 * @Date 2021/3/28  19:33
 */
public interface CourseFrontService {
    /****
     *
     * @param courseId 课程id
     * @param request 请求
     * @return
     */
    Result getFrontCourseInfoByCourseId(Integer courseId, HttpServletRequest request);
}
