package com.lcc.eduservice.client;

import com.lcc.eduservice.client.ipml.VodFileDegradeFeignClient;
import com.lcc.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: 一十六
 * @Description: 调用注册中心中提供的服务
 * @Date: 2020/5/30 14:51
 */
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class) //注册中心  需要调用的服务的名字
@Component
public interface VodClient {

    /**
     * 根据视频id删除阿里云视频
     * <p>
     * 方法的路径  是提供服务方的完整路径，建议把路径和方法名一起复制过来
     * <p>
     * ！！！注意：@PathVariable  注解中一定要指定参数名称，否则会出错
     *
     * @param id
     * @return
     */
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    Result removeAlyVideo(@PathVariable("id") String id);

    /**
     * 删除课程时，删除课程下的一个或者多个视频
     *
     * @param videoIdList
     * @return
     */
    @DeleteMapping("/eduvod/video/delete-batch")
    Result deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
