package com.lcc.eduservice.controller;


import com.lcc.eduservice.entity.CrmBanner;
import com.lcc.eduservice.entity.vo.BannerPageInfo;
import com.lcc.eduservice.entity.vo.BannerQuery;
import com.lcc.eduservice.service.CrmBannerService;
import com.lcc.result.Result;
import com.lcc.servicebase.valid.AddGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 管理员
 * 首页banner表 前端控制器
 * </p>
 *
 * @author chaochao
 * @since 2020-05-30
 */
@Api(value = "轮播图-后台管理")
@RestController
@RequestMapping("/educms/banneradmin")
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation("分页轮播图")
    @PostMapping("/pageBanner/{current}/{size}")
    public Result pageBanner(@PathVariable long current, @PathVariable long size,
                             @RequestBody(required = false) BannerQuery bannerQuery) {
        BannerPageInfo bannerPageInfo = bannerService.selectAllBannerPageInfo(current, size, bannerQuery);
        long total = bannerPageInfo.getTotal();
        List<CrmBanner> bannerList = bannerPageInfo.getBannerList();
        return Result.ok().data("items", bannerList).data("total", total);
    }

    @ApiOperation("添加轮播图")
    @CacheEvict(value = {"bannerFront"}, key = "'selectAllBanner'")
    @PostMapping("/addBanner")
    public Result addBanner(@Validated({AddGroup.class}) @RequestBody CrmBanner crmBanner) {
        bannerService.save(crmBanner);
        return Result.ok();
    }

    @ApiOperation(value = "修改轮播图")
    @CacheEvict(value = {"bannerFront"}, key = "'selectAllBanner'")
    @PutMapping("/updateBanner")
    public Result updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return Result.ok();
    }

    @ApiOperation(value = "根据id删除轮播图")
    @CacheEvict(value = {"bannerFront"}, key = "'selectAllBanner'")
    @DeleteMapping("/removeBanner/{id}")
    public Result remove(@ApiParam(name = "id", value = "轮播图id", required = true)
                         @PathVariable Integer id) {
        bannerService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "根据id查询轮播图")
    @GetMapping("/getBanner/{id}")
    public Result get(@ApiParam(name = "id", value = "轮播图id", required = true)
                      @PathVariable Integer id) {
        CrmBanner banner = bannerService.getById(id);
        return Result.ok().data("item", banner);
    }

}

