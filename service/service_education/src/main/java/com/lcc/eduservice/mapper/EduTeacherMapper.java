package com.lcc.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lcc.eduservice.entity.EduTeacher;
import com.lcc.eduservice.entity.vo.teacher.TeacherQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 讲师 Mapper 接口
 * </p>
 *
 * @author chaochao
 * @since 2020-05-25
 */
public interface EduTeacherMapper extends BaseMapper<EduTeacher> {

    /**
     * 讲师列表查询信息
     *
     * @param teacherQuery 查询条件
     * @return 讲师集合信息
     */
    List<EduTeacher> selectTeacherList(@Param("teacherQuery") TeacherQuery teacherQuery);
}
