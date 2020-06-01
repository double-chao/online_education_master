package com.lcc.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcc.eduservice.entity.CrmBanner;
import com.lcc.eduservice.mapper.CrmBannerMapper;
import com.lcc.eduservice.service.CrmBannerService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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

    //根据方法对其结果进行缓存，下次请求时，若缓存存在，直接返回缓存数据，不存在则执行方法，并把结果存入缓存
    @Cacheable(key = "'selectIndexList'",value = "banner") //一般用在查询方法上
    @Override
    public List<CrmBanner> selectAllBanner() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 4"); //id降序，得到前两条
        return baseMapper.selectList(wrapper);
    }
}
