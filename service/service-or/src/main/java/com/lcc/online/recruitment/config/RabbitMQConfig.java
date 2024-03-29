package com.lcc.online.recruitment.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @Author Administrator
 * @Description
 * @Date 2021/8/7  11:34
 */
@Slf4j
@Configuration
public class RabbitMQConfig {

    private RabbitTemplate rabbitTemplate;

    @Primary
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        this.rabbitTemplate = rabbitTemplate;

        rabbitTemplate.setMessageConverter(messageConverter());

        initRabbitTemplate();
        return rabbitTemplate;
    }


    /**
     * 定义RabbitMQ序列化器
     *
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 定制RabbitTemplate
     */
    //@PostConstruct // MyRabbitConfig对象创建完成以后，调用这个方法
    public void initRabbitTemplate() {
        // 设置回调 消息抵达Broker回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             * 只要消息抵达Broker ack就会是true
             * @param correlationData 当前消息唯一关联数据（唯一id）
             * @param ack 消息是否成功收到
             * @param cause 失败的原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("confirm...correlationData[{}],ack[{}],cause[{}]", correlationData, ack, cause);
            }
        });

        // 设置消息抵达队列Queue的确认回调
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             * 只要消息没有投递给指定的队列，就触发这个失败回调
             * @param message 投递失败的消息的详细信息
             * @param replyCode 回复的状态码
             * @param replyText 回复的文本内容
             * @param exchange 发送时的交换机
             * @param routingKey 发送时的路由键
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.debug("Fail Message[{}],replyCode[{}]", message, replyCode);
            }
        });

        //  rabbitTemplate.setReturnCallback((Message message, int replyCode, String replyText, String exchange, String routingKey)->{
        //
        //        });
    }
}
