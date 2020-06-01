package com.lcc.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcc.eduservice.entity.CrmBanner;
import com.lcc.eduservice.service.CrmBannerService;
import com.lcc.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 管理员
 * 首页banner表 前端控制器
 * </p>
 *
 * @author chaochao
 * @since 2020-05-30
 */
@Api(description = "轮播图-后台管理")
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation("分页轮播图")
    @GetMapping("/pageBanner/{current}/{size}")
    public Result pageBanner(@PathVariable long current, @PathVariable long size) {
        Page<CrmBanner> pageBanner = new Page<>(current, size);
        bannerService.page(pageBanner, null);
        return Result.ok().data("items", pageBanner.getRecords()).data("total", pageBanner.getTotal());
    }

    @ApiOperation("添加轮播图")
    @PostMapping("/addBanner")
    public Result addBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.save(crmBanner);
        return Result.ok();
    }

    @ApiOperation(value = "修改轮播图")
    @PutMapping("/updateBanner")
    public Result updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return Result.ok();
    }

    @ApiOperation(value = "根据id删除轮播图")
    @DeleteMapping("/removeBanner/{id}")
    public Result remove(@ApiParam(name = "id", value = "轮播图id", required = true)
                         @PathVariable String id) {
        bannerService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "根据id查询轮播图")
    @GetMapping("/getBanner/{id}")
    public Result get(@ApiParam(name = "id", value = "轮播图id", required = true)
                      @PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return Result.ok().data("item", banner);
    }

}

