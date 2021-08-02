package com.lcc.elasticsearch.client;

import com.lcc.elasticsearch.client.impl.TeacherClientImpl;
import com.lcc.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author Administrator
 * @Description 远程调用讲师信息接口
 * @Date 2021/7/27  20:01
 */
@FeignClient(name = "service-education", fallback = TeacherClientImpl.class)
@Component
public interface TeacherClient {

    @ApiOperation("根据id查询讲师信息")
    @GetMapping("/eduservice/teacher/getTeacher/{id}")
    Result getTeacher(@PathVariable Integer id);
}
