package com.lcc.eduservice.controller;


import com.lcc.eduservice.entity.CrmBanner;
import com.lcc.eduservice.service.CrmBannerService;
import com.lcc.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *     前台
 * 首页banner表 前端控制器
 * </p>
 *
 * @author chaochao
 * @since 2020-05-30
 */
@Api(description = "轮播图-前台显示")
@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation("首页轮播图")
    @GetMapping("/getAllBanner")
    public Result getAllBanner() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        return Result.ok().data("list",list);
    }

}

