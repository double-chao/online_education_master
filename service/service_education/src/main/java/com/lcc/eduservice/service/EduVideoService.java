package com.lcc.eduservice.service;

import com.lcc.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程小节视频 服务类
 * </p>
 *
 * @author chaochao
 * @since 2020-05-28
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 删除小节
     * @param courseId
     */
    void removeVideoByCourseId(String courseId);
}
