package com.lcc.eduservice.service;

import com.lcc.eduservice.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * 查询轮播图
     * @return
     */
    List<CrmBanner> selectAllBanner();
}
