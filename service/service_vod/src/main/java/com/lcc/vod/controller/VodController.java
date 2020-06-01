package com.lcc.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.util.Result;
import com.lcc.vod.service.VodService;
import com.lcc.vod.utils.ConstantVodUtils;
import com.lcc.vod.vo.InitVodClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程小节视频 前端控制器 阿里云操作
 * </p>
 *
 * @author chaochao
 * @since 2020-05-30
 */
@Api(description = "上传视频")
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @ApiOperation("上传视频到阿里云")
    @PostMapping("/uploadAlyVideo")
    public Result uploadAlyVideo(MultipartFile file) {
        //返回上传视频id
        String videoId = vodService.uploadVideoAly(file);
        return Result.ok().data("videoId", videoId);
    }

    @ApiOperation("根据id删除阿里云的视频")
    @DeleteMapping("/removeAlyVideo/{id}")
    public Result removeAlyVideo(@ApiParam(name = "id", value = "视频id", required = true)
                                 @PathVariable String id) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadException(20001, "删除视频失败");
        }
    }

    @ApiOperation("删除课程，删除下面的多个视频")
    @DeleteMapping("/delete-batch")
    public Result deleteBatch(@ApiParam(name = "videoIdList", value = "阿里云视频id集合", required = true)
                              @RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreAlyVideo(videoIdList);
        return Result.ok();
    }

}
