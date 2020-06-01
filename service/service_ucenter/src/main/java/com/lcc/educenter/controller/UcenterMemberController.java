package com.lcc.educenter.controller;


import com.lcc.educenter.entity.UcenterMember;
import com.lcc.educenter.entity.vo.RegisterVo;
import com.lcc.educenter.service.UcenterMemberService;
import com.lcc.util.JwtUtils;
import com.lcc.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author chaochao
 * @since 2020-05-31
 */
@Api(description = "登录/注册")
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result loginUser(@RequestBody UcenterMember member) {
        String token = memberService.login(member);
        return Result.ok().data("token",token);
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public Result registerUser(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return Result.ok();
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/getMemberInfo")
    public Result getMemberInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request); //根据token得到用户id
        UcenterMember member = memberService.getById(memberId);  //得到用户全部信息
        return Result.ok().data("userInfo",member);
    }

}

