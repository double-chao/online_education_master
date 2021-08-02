package com.lcc.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lcc.eduservice.entity.EduTeacher;
import com.lcc.eduservice.entity.vo.ObjectPageInfo;
import com.lcc.eduservice.entity.vo.teacher.TeacherQuery;
import com.lcc.result.Result;
import com.lcc.vo.PageVO;
import org.springframework.web.multipart.MultipartFile;

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
     *
     * @param current      当前页
     * @param size         多少条
     * @param teacherQuery 查询条件
     * @return 返回讲师集合信息
     */
    @Deprecated
    ObjectPageInfo selectAllTeacherPageInfo(long current, long size, TeacherQuery teacherQuery);

    /**
     * 后台管理 ---》讲师列表分页展示 ---> 自己写sql语句，不用mybatis-plus的api
     *
     * @param pageVO       分页对象
     * @param teacherQuery 查询条件
     * @return 讲师返回信息集合
     */
    Result listTeacher(PageVO pageVO, TeacherQuery teacherQuery);

    /**
     * 前台---》首页--讲师分页
     *
     * @param pageTeacher 分页对象
     * @return 返回值
     */
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);

    /***
     * excel导入讲师信息
     * @param file excel文件
     * @param teacherService 注入对象
     * @return 成功与否
     */
    boolean importExcelTeacher(MultipartFile file, EduTeacherService teacherService);
}
