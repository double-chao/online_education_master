package com.lcc.eduservice.entity.vo;

import com.lcc.eduservice.entity.CrmBanner;
import lombok.Data;

import java.util.List;

/**
 * @Author Administrator
 * @Description 轮播图分页对象
 * @Date 2020/8/28  16:31
 */
@Data
public class BannerPageInfo {
    private long total;
    private List<CrmBanner> bannerList;
}
