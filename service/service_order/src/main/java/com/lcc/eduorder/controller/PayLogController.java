package com.lcc.eduorder.controller;


import com.lcc.eduorder.service.PayLogService;
import com.lcc.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author chaochao
 * @since 2020-06-02
 */
@Api(description = "订单支付")
@RestController
@RequestMapping("/eduorder/paylog")
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    @ApiOperation("创建微信支付码")
    @GetMapping("/createNative/{orderNo}")
    public Result createNative(@PathVariable String orderNo) {
        Map map = payLogService.createNative(orderNo);
        return Result.ok().data(map);
    }

    @ApiOperation("查询支付状态")
    @GetMapping("/queryPayStatus/{orderNo}")
    public Result queryPayStatus(@PathVariable String orderNo) {
        Map<String, String> map = payLogService.queryPayStatus(orderNo); //根据订单id查询支付状态
        if (map == null) {
            return Result.fail().message("支付失败");
        }
        if (map.get("trade_state").equals("SUCCESS")) { //订单的支付状态成功
            payLogService.updateOrdersStatus(map); //更新订单状态
            return Result.ok().message("支付成功");
        }
        //在前端设置了一个定时器，每个多少秒去查询订单支付状态，所以这里返回了一个结果码，用于前端拦截器去判断是不是在支付中
        return Result.ok().code(25000).message("正在支付中");
    }

}

