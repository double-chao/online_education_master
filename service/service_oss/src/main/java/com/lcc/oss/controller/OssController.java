package com.lcc.oss.controller;

import com.lcc.oss.service.OssService;
import com.lcc.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: 一十六
 * @Description: 传输文件控制层
 * @Date: 2020/5/27 15:02
 */
@Api(description = "文件上传")
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin //跨域
public class OssController {

    @Autowired
    private OssService ossService;

    @ApiOperation("上传文件")
    @PostMapping
    public Result uploadOssFile(MultipartFile file) {
        //获取上传文件  MultipartFile
        //返回上传到oss的路径
        String url = ossService.uploadFileAvatar(file);
        return Result.ok().data("url",url);
    }
}
