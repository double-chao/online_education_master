package com.lcc.eduorder.service;

import com.lcc.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author chaochao
 * @since 2020-06-02
 */
public interface PayLogService extends IService<PayLog> {

    /**
     * 生成微信支付二维码
     * @param orderNo
     * @return
     */
    Map createNative(String orderNo);

    /**
     * 查询支付状态
     * @param orderNo
     * @return
     */
    Map<String, String> queryPayStatus(String orderNo);

    /**
     * 更新订单状态
     * @param map
     */
    void updateOrdersStatus(Map<String, String> map);
}
