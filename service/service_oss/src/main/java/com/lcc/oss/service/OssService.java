package com.lcc.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: 一十六
 * @Description: ${description}
 * @Date: 2020/5/27 15:05
 */
public interface OssService {
    //上传头像到oss
    String uploadFileAvatar(MultipartFile file);
}
