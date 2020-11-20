package com.lcc.eduservice.controller;


import com.lcc.eduservice.entity.EduArticle;
import com.lcc.eduservice.entity.dto.ArticleDTO;
import com.lcc.eduservice.entity.vo.ArticleInfoVO;
import com.lcc.eduservice.service.EduArticleService;
import com.lcc.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 文章 前端控制器
 * </p>
 *
 * @author chaochao
 * @since 2020-09-10
 */
@Api(description = "文章")
@RestController
@RequestMapping("/eduservice/article")
public class EduArticleController {

    @Autowired
    private EduArticleService articleService;

    @ApiOperation("添加文章-直接发布")
    @PostMapping("/directlyPublishArticle")

    public Result directlyPublishArticle(@RequestBody ArticleInfoVO articleInfoVO, HttpServletRequest request) {
        boolean flag = articleService.directlyPublishArticle(articleInfoVO, request);
        return flag ? Result.ok() : Result.fail();
    }

    @ApiOperation("添加文章-存为草稿")
    @PostMapping("/insertArticle")
    public Result insertArticle(@RequestBody ArticleInfoVO articleInfoVO, HttpServletRequest request) {
        boolean flag = articleService.saveArticle(articleInfoVO, request);
        return flag ? Result.ok() : Result.fail();
    }

    @ApiOperation("添加文章-草稿-发布")
    @PostMapping("/publishArticle/{articleID}")
    public Result publishArticle(
            @ApiParam(name = "articleID", value = "文章id", required = true) @PathVariable String articleID) {
        EduArticle article = new EduArticle();
        article.setId(articleID);
        article.setStatus("Normal");
        articleService.updateById(article);
        return Result.ok();
    }

    @ApiOperation("分页获取所有文章")
    @PostMapping("/listArticle/{current}/{size}")
    public Result listArticle(@PathVariable long current, @PathVariable long size,
                              @RequestBody(required = false) ArticleDTO articleDTO) {
        Map<String, Object> map = articleService.listArticle(current, size, articleDTO);
        return Result.ok().data(map);
    }

    @ApiOperation("根据id查询文章详情")
    @GetMapping("/getArticleById/{id}")
    public Result getArticleById(@PathVariable String id) {
        ArticleInfoVO articleInfoVO = articleService.getArticleInfoVOById(id);
        return Result.ok().data("articleInfoVO", articleInfoVO);
    }

    @ApiOperation("根据id删除文章")
    @DeleteMapping("{id}")
    public Result delArticleById(@ApiParam(name = "id", value = "文章id", required = true) @PathVariable String id) {
        return articleService.removeById(id) ? Result.ok() : Result.fail();
    }
}

