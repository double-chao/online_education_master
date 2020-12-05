package com.lcc.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.result.Result;
import com.lcc.servicebase.exceptionhandler.CodeEnum;
import com.lcc.vod.service.VodService;
import com.lcc.vod.utils.AliyunConstantUtils;
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
@Api(description = "阿里云视频")
@RestController
@RequestMapping("/eduvod/video")
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

    @ApiOperation("基于OSS上传视频到阿里云")
    @PostMapping("/uploadVideoAlyByOSS/{localFile}")
    public Result uploadVideoAlyByOSS(@PathVariable String localFile) {
        //返回上传视频id
        String videoId = vodService.uploadVideoAlyByOSS(localFile);
        return Result.ok().data("videoId", videoId);
    }

    @ApiOperation("根据id删除阿里云的视频")
    @DeleteMapping("/removeAlyVideo/{id}")
    public Result removeAlyVideo(@ApiParam(name = "id", value = "视频id", required = true)
                                 @PathVariable String id) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(AliyunConstantUtils.ACCESS_KEY_ID, AliyunConstantUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
            return Result.ok();
        } catch (Exception e) {
            throw new BadException(CodeEnum.DELETED_VIDEO_FAILED);
        }
    }

    @ApiOperation("删除课程，删除下面的多个视频")
    @DeleteMapping("/delete-batch")
    public Result deleteBatch(@ApiParam(name = "videoIdList", value = "阿里云视频id集合", required = true)
                              @RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreAlyVideo(videoIdList);
        return Result.ok();
    }

    @ApiOperation("根据阿里云视频id获取凭证")
    @GetMapping("/getPlayAuth/{id}")
    public Result getPlayAuth(@ApiParam(name = "id", value = "阿里云视频id", required = true)
                              @PathVariable String id) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(AliyunConstantUtils.ACCESS_KEY_ID,
                    AliyunConstantUtils.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return Result.ok().data("playAuth", playAuth);
        } catch (Exception e) {
            throw new BadException(CodeEnum.GET_VIDEO_PROOF_FAILED);
        }
    }

}
