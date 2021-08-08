package com.lcc.eduorder.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;

/**
 * @Author Administrator
 * @Description RabbitMq消息队列配置
 * @Date 2021/7/8  23:16
 */
@Slf4j
@Configuration
public class MyRabbitMQConfig {

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
     * @return MessageConverter
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 定制RabbitTemplate
     */
    public void initRabbitTemplate() {
        // 设置回调 消息抵达Broker回调
//        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
//            /**
//             * 只要消息抵达Broker ack就会是true
//             * @param correlationData 当前消息唯一关联数据（唯一id）
//             * @param ack 消息是否成功收到
//             * @param cause 失败的原因
//             */
//            @Override
//            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//                log.info("confirm...correlationData[{}],ack[{}],cause[{}]", correlationData, ack, cause);
//            }
//        });

        /*
         * 消息抵达Broker回调, 只要消息抵达Broker ack就会是true
         *
         * correlationData 当前消息唯一关联数据（唯一id）
         * ack 消息是否成功收到
         * cause 失败的原因
         */
        rabbitTemplate.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause) -> {
            log.info("消息抵达Broker回调，当前消息唯一关联数据：correlationData = {}", correlationData);
            log.info("消息抵达Broker回调，消息是否成功收到：ack = {}", ack);
            log.info("消息抵达Broker回调，失败的原因：cause = {}", cause);
        });

        // 设置消息抵达队列Queue的确认回调
//        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
//            /**
//             * 只要消息没有投递给指定的队列，就触发这个失败回调
//             * @param message 投递失败的消息的详细信息
//             * @param replyCode 回复的状态码
//             * @param replyText 回复的文本内容
//             * @param exchange 发送时的交换机
//             * @param routingKey 发送时的路由键
//             */
//            @Override
//            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//                log.info("message={}, message={}, replyText={}, exchange={}, routingKey={}", message, replyCode, replyText, exchange, routingKey);
//            }
//        });

        /*
         * 设置消息抵达队列Queue的确认回调，只要消息没有投递给指定的队列，就触发这个失败回调
         *
         * message 投递失败的消息的详细信息
         * replyCode 回复的状态码
         * replyText 回复的文本内容
         * exchange 发送时的交换机
         * routingKey 发送时的路由键
         */
        rabbitTemplate.setReturnCallback((Message message, int replyCode, String replyText, String exchange, String routingKey) -> {
            log.info("失败回调，消息没有投递给指定的队列,详细信息为：message={}", message);
            log.info("失败回调，消息没有投递给指定的队列,状态码为：replyCode={}", replyCode);
            log.info("失败回调，消息没有投递给指定的队列,文本内容为：replyText={}", replyText);
            log.info("失败回调，消息没有投递给指定的队列,交换机为：exchange={}", exchange);
            log.info("失败回调，消息没有投递给指定的队列,路由键为：routingKey={}", routingKey);
        });
    }

    /*
     *  容器中的Queue、Exchange、Binding 会自动创建（在RabbitMQ）不存在的情况下
     */

    /*
        队列的构造方法中的参数的解释
        Queue(String name,  队列名字
        boolean durable,  是否持久化
        boolean exclusive,  是否排他
        boolean autoDelete, 是否自动删除
        Map<String, Object> arguments) 属性
    */

    /**
     * 创建一个普通队列
     *
     * @return 普通队列
     */
    @Bean
    public Queue orderReleaseQueue() {
        return new Queue("order.release.order.queue", true, false, false);
    }

    /**
     * 创建一个商品秒杀队列
     *
     * @return 商品秒杀队列
     */
    @Bean
    public Queue orderSecKillOrderQueue() {
        return new Queue("order.secondKill.order.queue", true, false, false);
    }

    /**
     * 创建一个死信队列
     *
     * @return 死信队列
     */
    @Bean
    public Queue orderDelayQueue() {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "order-event-exchange");
        arguments.put("x-dead-letter-routing-key", "order.release.order");
        arguments.put("x-message-ttl", 1800000); // 消息过期时间 30分钟
        return new Queue("order.delay.queue", true, false, false, arguments);
    }

    /*
        String name, 交换机名字
        boolean durable, 是否持久化
        boolean autoDelete, 是否自动删除
        Map<String, Object> arguments 参数配置
    */

    /**
     * 创建一个主题交换机TopicExchange
     *
     * @return 主题交换机
     */
    @Bean
    public Exchange orderEventExchange() {
        return new TopicExchange("order-event-exchange", true, false);
    }

    /*
         String destination, 目的地（队列名或者交换机名字）
         DestinationType destinationType, 目的地类型（Queue、Exchange）
         String exchange, 交换机名字
         String routingKey, 路由键
         Map<String, Object> arguments
    */

    @Bean
    public Binding orderCreateBinding() {
        return new Binding("order.delay.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.create.order",
                null);
    }

    @Bean
    public Binding orderReleaseBinding() {
        return new Binding("order.release.order.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.release.order",
                null);
    }

    /**
     * 订单释放和库存释放绑定
     *
     * @return 订单释放和库存释放绑定
     */
    @Bean
    public Binding orderReleaseOtherBinding() {
        return new Binding("stock.release.stock.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.release.other.#",
                null);
    }

    /**
     * 商品秒杀绑定
     *
     * @return 秒杀绑定
     */
    @Bean
    public Binding orderSecKillOrderQueueBinding() {
        return new Binding("order.secondKill.order.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.secondKill.order",
                null);
    }
}
