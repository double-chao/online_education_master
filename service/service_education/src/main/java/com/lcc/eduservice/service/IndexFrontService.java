package com.lcc.eduservice.service;

import java.util.Map;

/**
 * @Author Administrator
 * @Description 首页
 * @Date 2020/11/21  18:16
 */
public interface IndexFrontService {
    /**
     * 查询前8条热门课程，查询前4条名师
     * @return
     */
    Map getHotCourseAndTeacher();
}
