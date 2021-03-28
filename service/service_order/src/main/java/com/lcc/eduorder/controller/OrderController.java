package com.lcc.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcc.eduorder.entity.Order;
import com.lcc.eduorder.entity.vo.OrderQueryVo;
import com.lcc.eduorder.service.OrderService;
import com.lcc.result.Result;
import com.lcc.util.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author chaochao
 * @since 2020-06-02
 */
@Api(value = "订单")
@RestController
@RequestMapping("/eduorder/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("创建订单")
    @PostMapping("/createOrder/{courseId}/{token}")
    public Result saveOrder(@PathVariable Integer courseId, @PathVariable String token, HttpServletRequest request) {
        String orderNo = orderService.createOrders(courseId, token, JwtUtils.getMemberIdByJwtToken(request));
        return Result.ok().data("orderId", orderNo);
    }

    @ApiOperation("根据订单id-查询订单信息")
    @GetMapping("/getOrderInfo/{orderId}")
    public Result getOrderInfo(@PathVariable Integer orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderId);
        Order order = orderService.getOne(wrapper);
        return Result.ok().data("item", order);
    }

    @ApiOperation("课程是否被购买")
//    @AnonymousAccess
    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable Integer courseId, @PathVariable Integer memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1);
        return orderService.count(wrapper) > 0;
    }

    @ApiOperation("订单列表分页展示")
    @PostMapping("/pageOrderCondition/{current}/{size}")
    public Result pageOrderCondition(@PathVariable long current, @PathVariable long size,
                                     @RequestBody(required = false) OrderQueryVo orderQueryVo) {
        Page<Order> orderPage = new Page<>(current, size);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        Integer status = orderQueryVo.getStatus();
        String begin = orderQueryVo.getBegin();
        String end = orderQueryVo.getEnd();
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        wrapper.orderByDesc("gmt_create"); //创建时间排序
        orderService.page(orderPage, wrapper);
        long total = orderPage.getTotal(); //总记录条数
        List<Order> orderList = orderPage.getRecords(); //集合对象
        return Result.ok().data("total", total).data("orderList", orderList);
    }

    @ApiOperation("根据订单id-删除订单")
    @DeleteMapping("/deleteOrderById/{orderId}")
    public Result deleteOrderById(@PathVariable Integer orderId) {
        return orderService.removeById(orderId) ? Result.ok() : Result.fail();
    }
}

