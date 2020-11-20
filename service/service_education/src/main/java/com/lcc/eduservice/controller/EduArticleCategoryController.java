package com.lcc.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcc.eduservice.entity.EduArticleCategory;
import com.lcc.eduservice.service.EduArticleCategoryService;
import com.lcc.result.Result;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.servicebase.valid.AddGroup;
import com.lcc.servicebase.valid.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 文章类别 前端控制器
 * </p>
 *
 * @author chaochao
 * @since 2020-10-16
 */
@Api(description = "文章类别")
@RestController
@RequestMapping("/eduservice/category")
public class EduArticleCategoryController {

    @Autowired
    private EduArticleCategoryService categoryService;

    @ApiOperation("查询所有类别")
    @PostMapping("/listCategory/{page}/{limit}")
    public Result listCategory(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit, @RequestBody(required = false) EduArticleCategory category) {
        Page<EduArticleCategory> pageParam = new Page<>(page, limit);
        QueryWrapper<EduArticleCategory> wrapper = new QueryWrapper<>();
        String name = category.getName();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        categoryService.page(pageParam, wrapper);
        List<EduArticleCategory> categoryList = pageParam.getRecords();
        long total = pageParam.getTotal();
        return Result.ok().data("categoryList", categoryList).data("total", total);
    }

    @ApiOperation("添加文章分类")
    @PostMapping("/insertCategory")
    public Result insertCategory(@Validated({AddGroup.class}) @RequestBody EduArticleCategory category) {
        String name = category.getName();
        QueryWrapper<EduArticleCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        EduArticleCategory articleCategory = categoryService.getOne(wrapper);
        if (!StringUtils.isEmpty(articleCategory)) {
            throw new BadException(20001, "文章分类已经存在");
        } else {
            boolean save = categoryService.save(category);
            return save ? Result.ok() : Result.fail();
        }
    }

    @ApiOperation("更新类别")
    @PostMapping("/updateCategory")
    public Result updateCategory(@Validated({UpdateGroup.class}) @RequestBody EduArticleCategory category) {
        boolean b = categoryService.updateById(category);
        return b ? Result.ok() : Result.fail();
    }

    @ApiOperation("根据id删除类别")
    @DeleteMapping("{id}")
    public Result deleteCategory(@PathVariable String id) {
        boolean b = categoryService.removeById(id);
        return b ? Result.ok() : Result.fail();
    }

    @ApiOperation("批量删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody Set<String> idSet) {
        boolean flag = categoryService.removeByIds(idSet);
        return flag ? Result.ok() : Result.fail();
    }

    @ApiOperation("根据类别id获取信息")
    @GetMapping("{id}")
    public Result getCategoryById(@PathVariable String id) {
        EduArticleCategory articleCategory = categoryService.getById(id);
        return Result.ok().data("articleCategoryInfo", articleCategory);
    }

}

