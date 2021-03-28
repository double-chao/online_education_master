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
     * @param courseId
     * @param token
     * @param memberId
     * @return
     */
    String createOrders(Integer courseId, String token, Integer memberId);

}
