package com.lcc.eduservice.service;

import com.lcc.eduservice.entity.EduArticleDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 文章内容 服务类
 * </p>
 *
 * @author chaochao
 * @since 2020-09-10
 */
public interface EduArticleDescriptionService extends IService<EduArticleDescription> {

    /**
     * 根据文章id 查询文章描述
     * @param id
     * @return
     */
    EduArticleDescription getByArticleId(String id);
}
