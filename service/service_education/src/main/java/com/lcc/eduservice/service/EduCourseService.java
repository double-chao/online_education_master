package com.lcc.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lcc.eduservice.entity.EduCourse;
import com.lcc.eduservice.entity.frontvo.CourseFrontVo;
import com.lcc.eduservice.entity.vo.CourseInfoVo;
import com.lcc.eduservice.entity.vo.CoursePublishVo;
import com.lcc.eduservice.entity.vo.CourseQuery;
import com.lcc.eduservice.entity.vo.ObjectPageInfo;

import java.util.Map;

/**
 * <p>
 * 课程基本信息 服务类
 * </p>
 *
 * @author chaochao
 * @since 2020-05-28
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 后台管理 ---> 课程分类查询
     * @return
     */
    ObjectPageInfo selectAllCoursePageInfo(long current, long size, CourseQuery courseQuery);

    /**
     * 添加课程信息
     * @param courseInfoVo
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据id得到课程对象信息
     * @param courseId
     * @return
     */
    CourseInfoVo getCourseInfo(String courseId);

    /**
     * 更新课程信息
     * @param courseInfoVo
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id得到发布时的课程信息
     * @param courseId
     * @return
     */
    CoursePublishVo getPublishVoInfo(String courseId);

    /**
     * 删除课程
     * @param courseId
     */
    void removeCourse(String courseId);

    /**
     * 前端页面课程分页查询
     * @param coursePage
     * @return
     */
    Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo);
}
