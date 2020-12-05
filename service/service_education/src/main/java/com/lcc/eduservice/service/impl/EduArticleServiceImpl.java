package com.lcc.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcc.eduservice.client.UserClient;
import com.lcc.eduservice.entity.EduArticle;
import com.lcc.eduservice.entity.EduArticleDescription;
import com.lcc.eduservice.entity.dto.ArticleDTO;
import com.lcc.eduservice.entity.vo.ArticleInfoVO;
import com.lcc.eduservice.mapper.EduArticleMapper;
import com.lcc.eduservice.service.EduArticleDescriptionService;
import com.lcc.eduservice.service.EduArticleService;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.servicebase.exceptionhandler.CodeEnum;
import com.lcc.util.JwtUtils;
import com.lcc.vo.UserOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 * 文章 服务实现类
 * </p>
 *
 * @author chaochao
 * @since 2020-09-10
 */
@Service
public class EduArticleServiceImpl extends ServiceImpl<EduArticleMapper, EduArticle> implements EduArticleService {

    @Autowired
    private EduArticleDescriptionService descriptionService;

    @Autowired
    private EduArticleService articleService;

    @Autowired
    private UserClient userClient;

    @Autowired
    private ThreadPoolExecutor poolExecutor;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean directlyPublishArticle(ArticleInfoVO articleInfoVO, HttpServletRequest request) {
        boolean checkToken = JwtUtils.checkToken(request);
        if (checkToken) {
            Integer memberId = JwtUtils.getMemberIdByJwtToken(request);
            if (0 == memberId) {
                throw new BadException(CodeEnum.USER_NO_LOGIN_EXCEPTION);
            }
            articleInfoVO.setMemberId(memberId);
            EduArticle article = new EduArticle();
            article.setCategoryId(articleInfoVO.getCategoryId());
            article.setStatus("Normal");
            BeanUtils.copyProperties(articleInfoVO, article);
            boolean b = articleService.save(article);
            if (b) {
                EduArticleDescription description = new EduArticleDescription();
                description.setArticleId(article.getId());
                description.setDescription(articleInfoVO.getDescription());
                return descriptionService.save(description);
            } else {
                throw new BadException(CodeEnum.OPERATE_EXCEPTION);
            }
        } else {
            throw new BadException(CodeEnum.LOGIN_EXPIRED_EXCEPTION);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean saveArticle(ArticleInfoVO articleInfoVO, HttpServletRequest request) {
        boolean checkToken = JwtUtils.checkToken(request);
        if (checkToken) {
            Integer memberId = JwtUtils.getMemberIdByJwtToken(request);
            if (0 == memberId) {
                throw new BadException(CodeEnum.USER_NO_LOGIN_EXCEPTION);
            }
            articleInfoVO.setMemberId(memberId);
            EduArticle article = new EduArticle();
            article.setCategoryId(articleInfoVO.getCategoryId());
            BeanUtils.copyProperties(articleInfoVO, article);
            boolean b = articleService.save(article);
            if (b) {
                EduArticleDescription description = new EduArticleDescription();
                description.setArticleId(article.getId());
                description.setDescription(articleInfoVO.getDescription());
                return descriptionService.save(description);
            } else {
                throw new BadException(CodeEnum.OPERATE_EXCEPTION);
            }
        } else {
            throw new BadException(CodeEnum.LOGIN_EXPIRED_EXCEPTION);
        }
    }

    @Override
    public ArticleInfoVO getArticleInfoVOById(Integer id) {
        ArticleInfoVO articleInfoVO = new ArticleInfoVO();
        CompletableFuture<EduArticle> articleFuture = CompletableFuture.supplyAsync(
                () -> this.baseMapper.selectById(id), poolExecutor);

        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
            EduArticleDescription description = descriptionService.getByArticleId(id);
            articleInfoVO.setDescription(description.getDescription());
        }, poolExecutor);

        CompletableFuture<Void> voidCompletableFuture = articleFuture.thenAcceptAsync((article) ->
                BeanUtils.copyProperties(article, articleInfoVO), poolExecutor);
        try {
            CompletableFuture.allOf(runAsync, voidCompletableFuture).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return articleInfoVO;
    }

    @Override
    public Map<String, Object> listArticle(long current, long size, ArticleDTO articleDTO) {
        Page<EduArticle> articlePage = new Page<>(current, size);
        QueryWrapper<EduArticle> wrapper = new QueryWrapper<>();
        String lastPublish = articleDTO.getLastPublish();
        String hot = articleDTO.getHot();
        String everyDay = articleDTO.getEveryDay();
        wrapper.eq("status", "Normal");
        if (!StringUtils.isEmpty(lastPublish)) { // 最新发表
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(hot)) { // 热点推荐
            wrapper.orderByDesc("scan_number");
            wrapper.orderByDesc("thumbs_up");
        }
        if (!StringUtils.isEmpty(everyDay)) { // 每日一博
            LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
            wrapper.ge("gmt_modified", startTime);
            wrapper.le("gmt_modified", endTime);
        }
        this.baseMapper.selectPage(articlePage, wrapper);
        List<EduArticle> records = articlePage.getRecords(); //多少条记录数
        List<ArticleInfoVO> articleInfoVOList = new ArrayList<>();
        for (EduArticle article : records) {
            Integer memberId = article.getMemberId();
            UserOrder userOrder = userClient.getUserInfoOrder(memberId);
            if (!StringUtils.isEmpty(userOrder)) {
                Integer id = article.getId();
                EduArticleDescription description = descriptionService.getByArticleId(id);
                ArticleInfoVO articleInfoVO = new ArticleInfoVO();
                articleInfoVO.setDescription(description.getDescription());

                articleInfoVO.setNickname(userOrder.getNickname());
                articleInfoVO.setAvatar(userOrder.getAvatar());

                BeanUtils.copyProperties(article, articleInfoVO);
                articleInfoVOList.add(articleInfoVO);
            } else {
                throw new BadException(CodeEnum.GET_USER_INFO_FAILED_EXCEPTION);
            }
        }
        long pageCurrent = articlePage.getCurrent();//当前页
        long pages = articlePage.getPages();//共多少页
        long pageSize = articlePage.getSize(); //一页多少条
        long total = articlePage.getTotal(); // 一共多少条
        boolean hasPrevious = articlePage.hasPrevious(); //是否有上一页
        boolean hasNext = articlePage.hasNext(); //是否有下一页
        Map<String, Object> map = new HashMap<>();
        map.put("articleInfoVOList", articleInfoVOList);
        map.put("pageCurrent", pageCurrent);
        map.put("pages", pages);
        map.put("pageSize", pageSize);
        map.put("total", total);
        map.put("hasPrevious", hasPrevious);
        map.put("hasNext", hasNext);
        return map;
    }
}
