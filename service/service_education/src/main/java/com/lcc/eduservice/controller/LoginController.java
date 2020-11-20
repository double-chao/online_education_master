package com.lcc.eduservice.controller;

import com.lcc.result.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 一十六
 * @Description: 登录
 * @Date: 2020/5/26
 */
@Api(description = "登录")
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

}
