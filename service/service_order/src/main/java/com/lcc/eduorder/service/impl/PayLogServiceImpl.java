package com.lcc.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import com.lcc.eduorder.client.CourseClient;
import com.lcc.eduorder.entity.Order;
import com.lcc.eduorder.entity.PayLog;
import com.lcc.eduorder.mapper.PayLogMapper;
import com.lcc.eduorder.service.OrderService;
import com.lcc.eduorder.service.PayLogService;
import com.lcc.eduorder.utils.HttpClient;
import com.lcc.servicebase.exceptionhandler.BadException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author chaochao
 * @since 2020-06-02
 */
@Slf4j
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CourseClient courseClient; // TODO 暂未写调用远程的方法

    @Autowired
    private PayLogService payLogService;

    @Override
    public Map<String, Object> createNative(String orderNo) {
        try {
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no", orderNo);
            Order order = orderService.getOne(wrapper);
            Map<String, String> m = new HashMap();
            m.put("appid", "wx74862e0dfcf69954"); //微信id
            m.put("mch_id", "1558950191"); //商户号
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", order.getCourseTitle()); //课程标题
            m.put("out_trade_no", orderNo); //订单号
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
            m.put("spbill_create_ip", "127.0.0.1"); //真实项目开发中，应该用项目的域名
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            m.put("trade_type", "NATIVE"); //
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            Map<String, Object> map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code")); //返回二维码状态
            map.put("code_url", resultMap.get("code_url")); //二维码地址
            return map;
        } catch (Exception e) {
            throw new BadException(20001, e.getMessage());
        }
    }

    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            Map<String, String> m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            String xml = client.getContent();
            return WXPayUtil.xmlToMap(xml);
        } catch (Exception e) {
            return null;
        }

    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void updateOrdersStatus(Map<String, String> map) {
        String orderNo = map.get("out_trade_no"); //获取订单id
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);
        if (order.getStatus().intValue() == 1) {
            return;
        }
        order.setStatus(1);
        // 2020.08.31 更新订单状态，添加分布式锁，暂未测试
        RLock lock = redissonClient.getLock("updateOrdersStatus-lock");
        try {
            boolean tryLock = lock.tryLock();
            if (tryLock) { //获取锁成功
                log.info("redisson加锁成功.....");
                orderService.updateById(order);
                // TODO 根据课程id更新课程购买/浏览数的信息,使用courseClient

                PayLog payLog = new PayLog();
                payLog.setOrderNo(orderNo);
                payLog.setPayType(1);
                payLog.setTotalFee(order.getTotalFee());
                payLog.setTradeState(map.get("trade_state")); //交易状态
                payLog.setTransactionId(map.get("transaction_id")); //订单流水号
                payLog.setAttr(JSONObject.toJSONString(map));
                payLogService.save(payLog);
            }
        } catch (Exception e) {
            throw new BadException(20001, "redisson获取锁失败.....");
        } finally {
            lock.unlock();
        }

    }
}
