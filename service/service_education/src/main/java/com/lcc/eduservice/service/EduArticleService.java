package com.lcc.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lcc.eduservice.entity.EduArticle;
import com.lcc.eduservice.entity.dto.ArticleDTO;
import com.lcc.eduservice.entity.vo.ArticleInfoVO;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 文章 服务类
 * </p>
 *
 * @author chaochao
 * @since 2020-09-10
 */
public interface EduArticleService extends IService<EduArticle> {

    /**
     * 新增文章 ->直接发布/存为草稿
     * @param articleInfoVO
     * @param request
     * @return
     */
    boolean directlyPublishOrInsertArticle(ArticleInfoVO articleInfoVO, HttpServletRequest request);

    /**
     * 分页查询 文章信息
     * @param current
     * @param size
     * @return
     */
    Map<String, Object> listArticle(long current, long size, ArticleDTO articleDTO);

    /**
     * 根据id查询文章详情
     * @param id
     * @return
     */
    ArticleInfoVO getArticleInfoVOById(Integer id);
}
