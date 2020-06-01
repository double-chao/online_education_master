package com.lcc.eduservice.service;

import com.lcc.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lcc.eduservice.entity.vo.CourseInfoVo;
import com.lcc.eduservice.entity.vo.CoursePublishVo;
import com.lcc.eduservice.entity.vo.CourseQuery;
import com.lcc.eduservice.entity.vo.ObjectPageInfo;

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
     *
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
}
