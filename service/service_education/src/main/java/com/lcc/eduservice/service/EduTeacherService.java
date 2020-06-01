package com.lcc.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcc.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * 首页--讲师分页
     * @param pageTeacher
     * @return
     */
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
