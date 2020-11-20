package com.lcc.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lcc.eduservice.entity.EduSubject;
import com.lcc.eduservice.entity.vo.subject.OneSubjectVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author chaochao
 * @since 2020-05-27
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 读取excel文件数据
     * @param file
     * @param subjectService
     */
    void saveSubjectFile(MultipartFile file,EduSubjectService subjectService);

    /**
     * 获取一级菜单以及二级菜单的课程科目信息
     *
     * @return
     */
    List<OneSubjectVO> getSubjectInfo();

    /**
     * 添加一级课程科目菜单
     *
     * @param eduSubject
     * @return
     */
    boolean saveOneSubject(EduSubject eduSubject);

    /**
     * 添加二级课程科目菜单
     *
     * @param id    一级课程科目id
     * @param sort  排序值
     * @param title 二级课程科目菜单名称
     * @return
     */
    boolean saveTwoSubjectById(String id, Integer sort, String title);
}
