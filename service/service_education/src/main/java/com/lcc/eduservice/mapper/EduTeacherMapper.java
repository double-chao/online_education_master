package com.lcc.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lcc.eduservice.entity.EduTeacher;
import com.lcc.eduservice.entity.vo.TeacherQuery;

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
    * 获取讲师信息
    * @param teacherQuery
    * @return
    */
   List<EduTeacher> selectTeacherList(TeacherQuery teacherQuery);
}
