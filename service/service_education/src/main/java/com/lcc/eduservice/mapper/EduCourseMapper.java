package com.lcc.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lcc.eduservice.entity.EduCourse;
import com.lcc.eduservice.entity.frontvo.CourseWebVo;
import com.lcc.eduservice.entity.vo.CoursePublishVo;
import com.lcc.eduservice.entity.vo.CourseQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author chaochao
 * @since 2020-05-29
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    /**
     * 根据课程id 得到发布课程的相关信息
     *
     * @param courseId 课程id
     * @return 发布课程信息
     */
    CoursePublishVo selectPublishVoInfo(Integer courseId);

    /**
     * 前端页面---》根据id查询课程信息
     *
     * @return 课程
     */
    CourseWebVo selectWebCourseInfo(Integer courseId);

    /**
     * 课程列表查询
     *
     * @param courseQuery 查询条件
     * @return 课程集合信息
     */
    List<EduCourse> selectCourseList(@Param("courseQuery") CourseQuery courseQuery);
}
