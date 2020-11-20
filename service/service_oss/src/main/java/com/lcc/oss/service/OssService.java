package com.lcc.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Author: 一十六
 * @Description: 文件上传
 * @Date: 2020/5/27 15:05
 */
public interface OssService {

    @Deprecated
    //上传头像到oss
    String uploadFileAvatar(MultipartFile file);

    /**
     * 上传文件
     *
     * @return
     */
    Map<String, String> uploadFile();
}
