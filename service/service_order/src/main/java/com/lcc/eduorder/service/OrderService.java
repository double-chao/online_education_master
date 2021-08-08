package com.lcc.eduorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lcc.eduorder.entity.Order;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author chaochao
 * @since 2020-06-02
 */
public interface OrderService extends IService<Order> {

    /**
     * 创建订单
     *
     * @param courseId 课程id
     * @param token 防重令牌
     * @param memberId 客户id
     * @return 返回信息
     */
    String createOrders(Integer courseId, String token, Integer memberId);

    /**
     * 关闭订单
     * @param order 订单实体
     * @return 返回标志，true:关闭成功，false:关闭失败
     */
    boolean closeOrder(Order order);

}
