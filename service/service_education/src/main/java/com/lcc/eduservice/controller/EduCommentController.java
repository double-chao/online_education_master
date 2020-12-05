package com.lcc.eduservice.controller;


import com.lcc.eduservice.entity.vo.CommentQuery;
import com.lcc.eduservice.entity.vo.ObjectPageInfo;
import com.lcc.eduservice.service.EduCommentService;
import com.lcc.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author chaochao
 * @since 2020-06-06
 */
@Api(description = "课程评论")
@RestController
@RequestMapping("/eduservice/comment")
public class EduCommentController {

    @Autowired
    private EduCommentService commentService;

    @ApiOperation("课程评论分类")
    @PostMapping("/pageCommentInfo/{current}/{size}")
    public Result pageCommentInfo(@PathVariable long current, @PathVariable long size,
                                  @RequestBody(required = false) CommentQuery commentQuery) {
        ObjectPageInfo objectPageInfo = commentService.selectCommentPageInfo(current, size, commentQuery);
        long total = objectPageInfo.getTotal();
        List list = objectPageInfo.getList();
        return Result.ok().data("total", total).data("commentList", list);
    }

    @ApiOperation("根据id-删除评论")
    @DeleteMapping("/deleteComment/{id}")
    public Result deleteComment(@PathVariable Integer id) {
        return commentService.removeById(id) ? Result.ok() : Result.fail();
    }

    @ApiOperation(value = "根据id列表-批量删除评论")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody Set<Integer> idList) {
        commentService.removeByIds(idList);
        return Result.ok();
    }
}

