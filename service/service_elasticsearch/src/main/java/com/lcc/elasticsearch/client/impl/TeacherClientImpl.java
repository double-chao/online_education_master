package com.lcc.elasticsearch.client.impl;

import com.lcc.elasticsearch.client.TeacherClient;
import com.lcc.result.Result;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.servicebase.exceptionhandler.CodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author Administrator
 * @Description
 * @Date 2021/7/27  20:01
 */
@Slf4j
@Component
public class TeacherClientImpl implements TeacherClient {

    @Override
    public Result getTeacher(Integer id) {
        log.error("远程调用讲师服务，执行熔断器，查询讲师信息失败");
        throw new BadException(CodeEnum.GET_TEACHER_INFO_EXCEPTION);
    }
}
