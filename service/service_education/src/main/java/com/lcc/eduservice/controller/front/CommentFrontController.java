package com.lcc.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcc.eduservice.entity.EduComment;
import com.lcc.eduservice.service.EduCommentService;
import com.lcc.result.Result;
import com.lcc.servicebase.valid.AddGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Administrator
 * @Description 前端页面controller-课程评论
 * @Date 2020/9/25
 */
@Api(value = "课程评论-前台")
@RestController
@RequestMapping("/eduservice/front/comment")
public class CommentFrontController {

    @Autowired
    private EduCommentService commentService;

    @ApiOperation(value = "根据课程id-获取课程的评论")
    @GetMapping("/listCommentByCourseId/{courseId}/{current}/{size}")
    public Result listCommentByCourseId(@PathVariable Integer courseId, @PathVariable long current, @PathVariable long size) {
        Page<EduComment> commentPage = new Page<>(current, size);
        Map<String, Object> map = commentService.listCourseFrontById(courseId,commentPage);
        return Result.ok().data(map);
    }

    @ApiOperation(value = "添加评论")
    @PostMapping("/insertComment")
    public Result insertComment(@Validated({AddGroup.class}) @RequestBody EduComment comment, HttpServletRequest request) {
        boolean flag = commentService.saveComment(comment, request);
        return flag ? Result.ok() : Result.fail();
    }

}
