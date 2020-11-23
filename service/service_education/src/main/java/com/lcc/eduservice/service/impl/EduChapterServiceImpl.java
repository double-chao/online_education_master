package com.lcc.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcc.eduservice.entity.EduChapter;
import com.lcc.eduservice.entity.EduVideo;
import com.lcc.eduservice.entity.chapter.ChapterVo;
import com.lcc.eduservice.entity.chapter.VideoVo;
import com.lcc.eduservice.mapper.EduChapterMapper;
import com.lcc.eduservice.service.EduChapterService;
import com.lcc.eduservice.service.EduVideoService;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.servicebase.exceptionhandler.CodeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程章节 服务实现类
 * </p>
 *
 * @author chaochao
 * @since 2020-05-28
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;
    @Autowired
    private EduChapterService chapterService;

    @Override
    public List<ChapterVo> getChapterAndVideoById(String courseId) {
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        List<EduChapter> chapterList = baseMapper.selectList(queryWrapper);

        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        List<EduVideo> videoList = videoService.list(videoQueryWrapper);

        List<ChapterVo> chapterVoList = new ArrayList<>(chapterList.size());
        for (int i = 0; i < chapterList.size(); i++) {
            EduChapter chapter = chapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            chapterVoList.add(chapterVo);

            List<VideoVo> videoVoList = new ArrayList<>(videoList.size());
            for (int i1 = 0; i1 < videoList.size(); i1++) {
                EduVideo video = videoList.get(i1);
                if (video.getChapterId().equals(chapter.getId())) { //小节的章节id == 章节的id
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
        }
        return chapterVoList;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean deleteChapter(String chapterId) {
        if (StringUtils.isEmpty(chapterId)) {
            throw new BadException(CodeEnum.SELECT_CHAPTER_NOT_EXITS);
        }
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapterId);
        int count = videoService.count(queryWrapper);
        // 删除章节时，章节下有小节，章节就不能删除
        if (count > 0) {
            throw new BadException(CodeEnum.DELETED_CHAPTER_FAILED);
        } else {
            return chapterService.deleteChapter(chapterId); //章节下没有小节，删除章节
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
