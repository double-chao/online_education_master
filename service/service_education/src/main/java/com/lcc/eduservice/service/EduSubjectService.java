package com.lcc.eduservice.service;

import com.lcc.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lcc.eduservice.entity.subject.OneSubject;
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
     * 获取一级分类二级分类
     * @return
     */
    List<OneSubject> getAllOneTwoSubject();
}
