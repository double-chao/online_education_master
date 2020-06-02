package com.lcc.eduservice.client.ipml;

import com.lcc.eduservice.client.VodClient;
import com.lcc.result.Result;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: 一十六
 * @Description: 服务熔断后，执行的方法
 * @Date: 2020/5/30 17:37
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {

    @Override
    public Result removeAlyVideo(String id) {
        return Result.fail().message("删除视频出错了");
    }

    @Override
    public Result deleteBatch(List<String> videoIdList) {
        return Result.fail().message("删除多个视频出错了");
    }

}
