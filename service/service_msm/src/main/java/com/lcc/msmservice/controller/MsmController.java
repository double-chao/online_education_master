package com.lcc.msmservice.controller;

import com.lcc.msmservice.service.MsmService;
import com.lcc.msmservice.utils.RandomUtil;
import com.lcc.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api(description = "短信服务")
@RestController
@RequestMapping("/edumsm/msm")
public class MsmController {

    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation("发送短信获取验证码")
    @GetMapping("/send/{phone}")
    public Result sendMsm(@PathVariable String phone) {
        //1 从redis获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return Result.ok();
        }
        //2 如果redis获取 不到，进行阿里云发送
        //生成随机值，传递阿里云进行发送
        code = RandomUtil.getSixBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        //调用service发送短信的方法
        boolean flag = msmService.send(param, phone);
        if (flag) {
            //发送成功，把发送成功验证码放到redis里面
            //设置有效时间  把输入的手机号码作为key值存到redis中
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return Result.ok();
        } else {
            return Result.fail().message("短信发送失败");
        }
    }


    @ApiOperation("发送短信获取验证码-2")
    @GetMapping("/sendMsmGetCode/{phone}")
    public Result sendMsmGetCode(@PathVariable String phone) {
        boolean flag = msmService.sendMsm(phone);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail().message("短信发送失败");
        }
    }
}
