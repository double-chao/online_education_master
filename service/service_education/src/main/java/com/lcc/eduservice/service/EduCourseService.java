package com.lcc.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lcc.eduservice.entity.EduCourse;
import com.lcc.eduservice.entity.frontvo.CourseFrontVo;
import com.lcc.eduservice.entity.frontvo.CourseWebVo;
import com.lcc.eduservice.entity.vo.CourseInfoVo;
import com.lcc.eduservice.entity.vo.CoursePublishVo;
import com.lcc.eduservice.entity.vo.CourseQuery;
import com.lcc.eduservice.entity.vo.ObjectPageInfo;
import com.lcc.result.Result;
import com.lcc.vo.PageVO;

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
     *
     * @return
     */
    @Deprecated
    ObjectPageInfo selectAllCoursePageInfo(long current, long size, CourseQuery courseQuery);

    /**
     * 课程列表信息查询
     *
     * @param pageVO      分页参数
     * @param courseQuery 查询参数
     * @return 课程信息列表
     */
    Result listCourse(PageVO pageVO, CourseQuery courseQuery);

    /**
     * 添加课程信息
     *
     * @param courseInfoVo
     */
    Integer saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据id得到课程对象信息
     *
     * @param courseId
     * @return
     */
    CourseInfoVo getCourseInfo(Integer courseId);

    /**
     * 更新课程信息
     *
     * @param courseInfoVo
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id得到发布时的课程信息
     *
     * @param courseId
     * @return
     */
    CoursePublishVo getPublishVoInfo(Integer courseId);

    /**
     * 删除课程
     *
     * @param courseId
     */
    void removeCourse(Integer courseId);

    /**
     * 前端页面---》课程分页查询
     *
     * @param coursePage
     * @return
     */
    Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo);

    /**
     * 前端页面---》课程信息
     *
     * @param courseId
     * @return
     */
    CourseWebVo getBaseCourseInfo(Integer courseId);
}
