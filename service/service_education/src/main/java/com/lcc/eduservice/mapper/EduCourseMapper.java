package com.lcc.eduservice.mapper;

import com.lcc.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lcc.eduservice.entity.vo.CoursePublishVo;

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
     * @param courseId
     * @return
     */
    CoursePublishVo selectPublishVoInfo(String courseId);
}
