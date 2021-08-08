package com.lcc.online.recruitment.service;

import com.lcc.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author Administrator
 * @Description
 * @Date 2021/7/5  22:55
 */
@Api(tags = "消息中间件服务类")
@RequestMapping("/online/recruitment/rabbitmq")
public interface RabbitMqService {

    @ApiOperation("创建一个直接类型的交换机")
    @GetMapping("/creatDirectExchange")
    Result creatDirectExchange();

    @ApiOperation("创建一个队列")
    @GetMapping("/createQueue")
    Result createQueue();

    @ApiOperation("将交换机与队列进行绑定")
    @GetMapping("/createBinding")
    Result createBinding();
}
