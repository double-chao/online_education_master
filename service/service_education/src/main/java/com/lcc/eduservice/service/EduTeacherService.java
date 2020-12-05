package com.lcc.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.lcc.eduservice.entity.EduTeacher;
import com.lcc.eduservice.entity.vo.ObjectPageInfo;
import com.lcc.eduservice.entity.vo.TeacherQuery;
import com.lcc.vo.PageVO;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author chaochao
 * @since 2020-05-25
 */
public interface EduTeacherService extends IService<EduTeacher> {


    /**
     * 后台管理 ---》讲师列表分页展示
     * @param current
     * @param size
     * @param teacherQuery
     * @return
     */
    ObjectPageInfo selectAllTeacherPageInfo(long current, long size, TeacherQuery teacherQuery);

    /**
     * 后台管理 ---》讲师列表分页展示 ---> 自己写sql语句，不用mybatisplus的api
     * @param pageVO
     * @param teacherQuery
     * @return
     */
    PageInfo<EduTeacher> listTeacher(PageVO pageVO, TeacherQuery teacherQuery);

    /**
     * 前台---》首页--讲师分页
     * @param pageTeacher
     * @return
     */
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
