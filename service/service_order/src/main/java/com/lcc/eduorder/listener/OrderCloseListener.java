package com.lcc.eduorder.listener;

import com.lcc.eduorder.entity.Order;
import com.lcc.eduorder.service.OrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author Administrator
 * @Description 关闭订单监听-消息队列
 * @Date 2021/8/7  12:12
 */
@Slf4j
@RabbitListener(queues = {"order.release.order.queue"})
@Service
public class OrderCloseListener {

    @Autowired
    private OrderService orderService;

    /**
     * 关闭订单
     *
     * @param order   订单实体类
     * @param channel 通道
     * @param message 消息
     * @throws IOException 异常
     */
    @RabbitHandler
    public void listener(Order order, Channel channel, Message message) throws IOException {
        log.info("收到过期的订单信息，准备关闭订单：" + order.getOrderNo());
        try {
            order.setStatus(2); // 关闭订单
            boolean flag = orderService.closeOrder(order);
            if (flag) {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                log.info("关闭订单成功：" + order.getOrderNo());
            } else {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
                log.info("关闭订单失败：" + order.getOrderNo());
            }
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            log.info("关闭订单异常：" + order.getOrderNo());
        }
    }
}
