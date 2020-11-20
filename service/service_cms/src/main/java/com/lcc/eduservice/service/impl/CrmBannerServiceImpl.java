package com.lcc.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcc.eduservice.entity.CrmBanner;
import com.lcc.eduservice.entity.vo.BannerPageInfo;
import com.lcc.eduservice.entity.vo.BannerQuery;
import com.lcc.eduservice.mapper.CrmBannerMapper;
import com.lcc.eduservice.service.CrmBannerService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author chaochao
 * @since 2020-05-30
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    /**
     * 使用spring注解缓存------第一步：在配置文件中指定缓存类型，如：spring.cache.type = redis
     * 第二步：在启动类上加注解：EnableCaching
     * 第三步：在方法上加注解，
     * 根据方法对其结果进行缓存，下次请求时，若缓存存在，直接返回缓存数据，不存在则执行方法，并把结果存入缓存
     * key:用SpEl表达式，在双引号中加单引号
     * value:相当于缓存的分区（一般按照业务类型分）
     * 过期时间（ttl）：在配置文件中加：spring.cache.redis.time-to-live = 1000 单位毫秒
     * 将数据保存为json格式：需要自定义缓存管理器
     * 第一步：
     * <p>
     * <p>
     * 使用spring cache不足之处：
     * 在读多写少的情况下能使用，不需要保证数据的实时性的，因为在写数据时，spring cache没有加锁。
     *
     * @return
     */
    @Cacheable(value = {"bannerFront"}, key = "#root.method.name") //一般用在查询方法上
    @Override
    public List<CrmBanner> selectAllBanner() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 4"); //id降序，得到前四条
        return baseMapper.selectList(wrapper);
    }

    @Override
    public BannerPageInfo selectAllBannerPageInfo(long current, long size, BannerQuery bannerQuery) {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        Page<CrmBanner> pageBanner = new Page<>(current, size);
        String title = bannerQuery.getTitle();
        boolean status = bannerQuery.isStatus();
        String begin = bannerQuery.getBegin();
        String end = bannerQuery.getEnd();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title); //双引号中的值为数据库语句中的字段名字
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        this.page(pageBanner, wrapper);
        long total = pageBanner.getTotal(); //总记录条数
        List<CrmBanner> bannerList = pageBanner.getRecords();//集合对象
        BannerPageInfo bannerPageInfo = new BannerPageInfo();
        bannerPageInfo.setTotal(total);
        bannerPageInfo.setBannerList(bannerList);
        return bannerPageInfo;
    }
}
