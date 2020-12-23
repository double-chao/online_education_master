package com.lcc.eduorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcc.eduorder.client.CourseClient;
import com.lcc.eduorder.client.UserClient;
import com.lcc.eduorder.entity.Order;
import com.lcc.eduorder.mapper.OrderMapper;
import com.lcc.eduorder.service.OrderService;
import com.lcc.eduorder.utils.OrderNoUtil;
import com.lcc.vo.CourseOrder;
import com.lcc.vo.UserOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author chaochao
 * @since 2020-06-02
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private UserClient userClient;
    @Autowired
    private CourseClient courseClient;

    @Override
    public String createOrders(Integer courseId, Integer memberId) {
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
        order.setStatus(0);  //支付状态 0未支付 1已支付
        order.setPayType(1); //支付类型 1微信 2支付宝
        baseMapper.insert(order);
        return order.getOrderNo(); //返回订单号
    }

}
