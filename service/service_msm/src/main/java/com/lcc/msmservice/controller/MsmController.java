package com.lcc.msmservice.controller;

import com.lcc.msmservice.service.MsmService;
import com.lcc.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "短信服务")
@RestController
@RequestMapping("/edumsm/msm")
public class MsmController {

    @Autowired
    private MsmService msmService;

    @ApiOperation("发送短信获取验证码")
    @GetMapping("/send/{phone}")
    public Result sendMsm(@PathVariable String phone) {
        boolean flag = msmService.sendMsm(phone);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail().message("短信发送失败");
        }
    }
}
