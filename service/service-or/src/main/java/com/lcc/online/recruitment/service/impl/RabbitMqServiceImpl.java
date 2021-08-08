package com.lcc.online.recruitment.service.impl;

import com.lcc.online.recruitment.service.RabbitMqService;
import com.lcc.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Administrator
 * @Description
 * @Date 2021/7/5  22:58
 */
@Slf4j
@RestController
public class RabbitMqServiceImpl implements RabbitMqService {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Result creatDirectExchange() {
        Exchange exchange = new DirectExchange("hello-java-exchange", true, false);
        amqpAdmin.declareExchange(exchange);
        return Result.ok().message("创建直接类型的交换机成功");
    }

    @Override
    public Result createQueue() {
        Queue queue = new Queue("hello-java-queue", true, false, false);
        amqpAdmin.declareQueue(queue);
        return Result.ok().message("创建直接类型的交换机成功");
    }

    @Override
    public Result createBinding() {
        Binding binding = new Binding("hello-java-queue", Binding.DestinationType.QUEUE, "hello-java-exchange", "hello.java", null);
        amqpAdmin.declareBinding(binding);
        return Result.ok().message("交换机hello-java-exchange与队列hello-java-queue绑定成功");
    }
}
