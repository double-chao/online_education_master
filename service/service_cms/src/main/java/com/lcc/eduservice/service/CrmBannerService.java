package com.lcc.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lcc.eduservice.entity.CrmBanner;
import com.lcc.eduservice.entity.vo.BannerPageInfo;
import com.lcc.eduservice.entity.vo.BannerQuery;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author chaochao
 * @since 2020-05-30
 */
public interface CrmBannerService extends IService<CrmBanner> {

    /**
     * 前端查询轮播图
     *
     * @return
     */
    List<CrmBanner> selectAllBanner();

    /**
     * 后端管理 查询所有轮播图
     * @param current 当前页
     * @param size 当前页大小
     * @param bannerQuery 查询条件
     * @return
     */
    BannerPageInfo selectAllBannerPageInfo(long current, long size, BannerQuery bannerQuery);
}
