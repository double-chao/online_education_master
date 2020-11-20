package com.lcc.eduservice.controller;

import com.lcc.eduservice.client.VodClient;
import com.lcc.eduservice.entity.EduVideo;
import com.lcc.eduservice.service.EduVideoService;
import com.lcc.result.Result;
import com.lcc.servicebase.exceptionhandler.BadException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程小节视频 前端控制器
 * </p>
 *
 * @author chaochao
 * @since 2020-05-28
 */
@Api(description = "小节数据")
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;
    @Autowired
    private VodClient vodClient; //注入在注册中心注册过的服务，调用其方法

    @ApiOperation("添加小节")
    @PostMapping("/addVideo")
    public Result addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return Result.ok();
    }

    // 这里要调用vod模块中的删除阿里云的视频方法，采用微服务的方式来进行删除，若采用依赖注入来用的话，就不符合微服务的思想了
    @ApiOperation("根据id删除小节")
    @DeleteMapping("{id}")
    public Result deleteVideo(@ApiParam(name = "id", value = "小节id", required = true)
                              @PathVariable String id) {
        EduVideo video = videoService.getById(id); // 根据小节id 得到视频id
        String videoId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoId)) {
            Result result = vodClient.removeAlyVideo(videoId);// 根据视频id 删除阿里云中的视频
            if (result.getCode() == 20001){
                throw new BadException(20001,"删除视频失败，熔断器执行了....");
            }
        }
        videoService.removeById(id); //删除小节
        return Result.ok();
    }
}

