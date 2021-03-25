package com.lcc.eduservice.controller;

import com.lcc.annotation.AccessLimit;
import com.lcc.result.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 一十六
 * @Description: 登录
 * @Date: 2020/5/26
 */
@Api(tags = "登录")
@Slf4j
@RestController
@RequestMapping("/eduservice/user")
public class LoginController {

    @PostMapping("/login")
    public Result login() {
        return Result.ok().data("token", "admin");

    }

    @GetMapping("/info")
    public Result info() {
        return Result.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    @AccessLimit(seconds = 5, maxCount = 100)
    @GetMapping("/test")
    public Result testAccessLimit() {
        return Result.ok();
    }

}
