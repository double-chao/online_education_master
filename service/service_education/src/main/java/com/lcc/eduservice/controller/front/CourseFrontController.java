package com.lcc.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcc.eduservice.client.OrderClient;
import com.lcc.eduservice.entity.EduCourse;
import com.lcc.eduservice.entity.chapter.ChapterVo;
import com.lcc.eduservice.entity.frontvo.CourseFrontVo;
import com.lcc.eduservice.entity.frontvo.CourseWebVo;
import com.lcc.eduservice.service.EduChapterService;
import com.lcc.eduservice.service.EduCourseService;
import com.lcc.result.Result;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.util.JwtUtils;
import com.lcc.vo.CourseOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: 一十六
 * @Description: 前台课程
 * @Date: 2020/6/1 20:29
 */
@Api(description = "前台课程")
@RestController
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private OrderClient orderClient;
    @Autowired
    private ThreadPoolExecutor poolExecutor;

    @ApiOperation("分页查询课程")
    @PostMapping("/getCourseFrontList/{current}/{size}")
    public Result getCourseFrontList(@PathVariable long current, @PathVariable long size,
                                     @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> coursePage = new Page<>(current, size);
        Map<String, Object> map = courseService.getCourseFrontList(coursePage, courseFrontVo);
        return Result.ok().data(map);
    }

    @ApiOperation("根据课程id查询课程信息")
    @GetMapping("/getFrontCourseInfo/{courseId}")
    public Result getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request) {
        boolean checkToken = JwtUtils.checkToken(request);
        if (checkToken) {
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            if (StringUtils.isEmpty(memberId)) {
                throw new BadException(20001, "用户未登录，请先登录");
            }
            CourseWebVo courseWebVo = new CourseWebVo();
            CompletableFuture<Void> courseWebVoFuture = CompletableFuture.runAsync(() -> {
                CourseWebVo getCourseWebVo = courseService.getBaseCourseInfo(courseId);
                BeanUtils.copyProperties(getCourseWebVo, courseWebVo);
            }, poolExecutor);

            List<ChapterVo> chapterVideoList = new ArrayList<>();
            CompletableFuture<Void> chapterVideoListFuture = CompletableFuture.runAsync(() -> {
                List<ChapterVo> getChapterVideoList = chapterService.getChapterAndVideoById(courseId);
                chapterVideoList.addAll(getChapterVideoList);
            }, poolExecutor);

            AtomicBoolean buyCourse = new AtomicBoolean(false);
            CompletableFuture<Void> buyCourseFuture = CompletableFuture.runAsync(() -> {
                boolean flag = orderClient.isBuyCourse(courseId, memberId);
                buyCourse.set(flag);
            }, poolExecutor);

            try {
                CompletableFuture.allOf(courseWebVoFuture,chapterVideoListFuture,buyCourseFuture).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return Result.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVideoList).data("isBuy", buyCourse);
        } else {
            throw new BadException(20001, "用户登录身份已过期");
        }
    }

    @ApiOperation("根据课程id查询-订单的课程信息")
    @PostMapping("/getOrderCourseInfo/{id}")
    public CourseOrder getOrderCourseInfo(@PathVariable String id) {
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
        CourseOrder courseOrder = new CourseOrder();
        BeanUtils.copyProperties(courseInfo, courseOrder);
        return courseOrder;
    }
}
