package com.lcc.eduservice.mapper;

import com.lcc.eduservice.entity.EduArticleDescription;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 文章内容 Mapper 接口
 * </p>
 *
 * @author chaochao
 * @since 2020-09-10
 */
public interface EduArticleDescriptionMapper extends BaseMapper<EduArticleDescription> {

    /**
     * 根据文章id 获取文章描述
     * @param id
     * @return
     */
    EduArticleDescription getArticleDescription(Integer id);
}
