package com.lcc.eduservice.client.ipml;

import com.lcc.eduservice.client.VodClient;
import com.lcc.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: 一十六
 * @Description: 服务熔断后，执行的方法
 * @Date: 2020/5/30 17:37
 */
@Slf4j
@Component
public class VodFileDegradeFeignClient implements VodClient {

    @Override
    public Result removeAlyVideo(String id) {
        log.error("远程调用视频服务，执行熔断器，删除视频出错了");
        return Result.fail().message("删除视频出错");
    }

    @Override
    public Result deleteBatch(List<String> videoIdList) {
        log.error("远程调用视频服务，执行熔断器，删除多个视频出错了");
        return Result.fail().message("删除多个视频出错");
    }

}
