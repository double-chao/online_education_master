package com.lcc.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lcc.eduservice.entity.EduChapter;
import com.lcc.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程章节 服务类
 * </p>
 *
 * @author chaochao
 * @since 2020-05-28
 */
public interface EduChapterService extends IService<EduChapter> {


    /**
     * 根据课程id得到章节和小节信息
     * @param courseId
     * @return
     */
    List<ChapterVo> getChapterAndVideoById(Integer courseId);

    /**
     * 删除章节根据章节id
     * @param chapterId
     * @return
     */
    boolean deleteChapter(Integer chapterId);

    /**
     * 删除章节根据课程id
     * @param courseId
     */
    void removeChapterByCourseId(Integer courseId);
}
