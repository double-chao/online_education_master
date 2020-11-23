package com.lcc.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcc.eduservice.client.VodClient;
import com.lcc.eduservice.entity.EduVideo;
import com.lcc.eduservice.mapper.EduVideoMapper;
import com.lcc.eduservice.service.EduVideoService;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.servicebase.exceptionhandler.CodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程小节视频 服务实现类
 * </p>
 *
 * @author chaochao
 * @since 2020-05-28
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public void removeVideoByCourseId(String courseId) {  // 先删除视频（即小节表中的视频id），在删除小节
        if (StringUtils.isEmpty(courseId)){
            throw new BadException(CodeEnum.COURSE_NOT_EXITS);
        }
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", courseId);
        videoWrapper.select("video_source_id");
        List<EduVideo> videoList = baseMapper.selectList(videoWrapper); //根据课程id得到小节对象集合
        if (!videoList.isEmpty()){
            List<String> videoSourceIdList = new ArrayList<>(videoList.size()); //
            for (int i = 0; i < videoList.size(); i++) { //遍历  得到每个小节对象的视频id
                EduVideo video = videoList.get(i);
                String videoSourceId = video.getVideoSourceId();
                if (!StringUtils.isEmpty(videoSourceId)) {
                    videoSourceIdList.add(videoSourceId);
                }
            }
            if (videoSourceIdList.size() > 0) {
                vodClient.deleteBatch(videoSourceIdList); //删除课程时，删除多个小节视频（阿里云中的视频）
            }
            QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
            wrapper.eq("course_id", courseId);
            baseMapper.delete(wrapper); //删除小节
        }else {
            throw new BadException(CodeEnum.SELECT_CHAPTER_NOT_EXITS);
        }

    }
}
