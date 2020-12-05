package com.lcc.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcc.eduservice.entity.EduArticleDescription;
import com.lcc.eduservice.mapper.EduArticleDescriptionMapper;
import com.lcc.eduservice.service.EduArticleDescriptionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章内容 服务实现类
 * </p>
 *
 * @author chaochao
 * @since 2020-09-10
 */
@Service
public class EduArticleDescriptionServiceImpl extends ServiceImpl<EduArticleDescriptionMapper, EduArticleDescription> implements EduArticleDescriptionService {

    @Override
    public EduArticleDescription getByArticleId(Integer id) {
        return this.baseMapper.getArticleDescription(id);
    }
}
