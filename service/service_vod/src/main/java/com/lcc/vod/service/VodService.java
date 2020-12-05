package com.lcc.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    /**
     *  上传视频到阿里云
     * @param file
     * @return
     */
    String uploadVideoAly(MultipartFile file);

    /**
     *  删除课程时，删除课程下的所有视频
     * @param videoIdList
     */
    void removeMoreAlyVideo(List<String> videoIdList);

    /**
     * 基于OSS上传视频到阿里云
     * @param localFile 需要上传到VOD的本地视频文件的完整路径，需要包含文件扩展名
     * @return
     */
    String uploadVideoAlyByOSS(String localFile);
}
