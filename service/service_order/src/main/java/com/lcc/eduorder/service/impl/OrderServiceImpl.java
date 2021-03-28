package com.lcc.eduorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcc.constant.OrderConstant;
import com.lcc.eduorder.client.CourseClient;
import com.lcc.eduorder.client.UserClient;
import com.lcc.eduorder.entity.Order;
import com.lcc.eduorder.mapper.OrderMapper;
import com.lcc.eduorder.service.OrderService;
import com.lcc.eduorder.utils.OrderNoUtil;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.servicebase.exceptionhandler.CodeEnum;
import com.lcc.vo.CourseOrder;
import com.lcc.vo.UserOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author chaochao
 * @since 2020-06-02
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private UserClient userClient;
    @Autowired
    private CourseClient courseClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String createOrders(Integer courseId, String token, Integer memberId) {
        log.info("前端页面防重令牌的值为：" + token);
        // 判断本次下单的token和redis存储的token是否一致 redis+lua脚本 原子验证令牌防止重复提交攻击
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        //  return 0 失败  1 成功
        Long result = stringRedisTemplate.execute(new DefaultRedisScript<>(script, Long.class),
                Arrays.asList(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberId), token);
        if (result == 0L) { // 令牌验证失败, 创建订单失败
            throw new BadException(CodeEnum.CREATE_ORDER_EXCEPTION);
        } else { // 令牌验证成功
            //用户信息
            UserOrder userOrder = userClient.getUserInfoOrder(memberId);
            //课程信息
            CourseOrder courseOrder = courseClient.getOrderCourseInfo(courseId);
            Order order = new Order();
            order.setOrderNo(OrderNoUtil.getOrderNo());// 订单号,随机生成
            order.setCourseId(courseId);
            order.setCourseTitle(courseOrder.getTitle());
            order.setCourseCover(courseOrder.getCover());
            order.setTeacherName(courseOrder.getTeacherName());
            order.setTotalFee(courseOrder.getPrice());

            order.setMemberId(memberId);
            order.setMobile(userOrder.getMobile());
            order.setNickname(userOrder.getNickname());
            order.setStatus(0);  // 支付状态 0未支付 1已支付
            order.setPayType(1); // 支付类型 1微信 2支付宝
            baseMapper.insert(order);
            return order.getOrderNo(); //返回订单号
        }
    }

}
