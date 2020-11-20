package com.lcc.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lcc.eduservice.entity.EduCourse;
import com.lcc.eduservice.entity.EduTeacher;
import com.lcc.eduservice.service.EduCourseService;
import com.lcc.eduservice.service.EduTeacherService;
import com.lcc.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@Api(description = "首页热门课程名师展示")
@RestController
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private ThreadPoolExecutor poolExecutor;

    //查询前8条热门课程，查询前4条名师
    @ApiOperation("首页课程、名师热门信息")
    @GetMapping("/index")
    public Result index() {
        List<EduCourse> eduList = new ArrayList<>();
        List<EduTeacher> teacherList = new ArrayList<>();
        CompletableFuture<List<EduCourse>> listCourseFuture = CompletableFuture.supplyAsync(() -> {
            QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
            wrapper.orderByDesc("id");
            wrapper.last("limit 8");  //查询前8条热门课程
            return courseService.list(wrapper);
        }, poolExecutor);
        CompletableFuture<Void> voidCompletableFuture1 = listCourseFuture.thenAcceptAsync((res) -> {
            eduList.addAll(res);
        }, poolExecutor);

        CompletableFuture<List<EduTeacher>> listTeacherFuture = CompletableFuture.supplyAsync(() -> {
            QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
            wrapperTeacher.orderByDesc("id");
            wrapperTeacher.last("limit 4");  //查询前4条名师
            return teacherService.list(wrapperTeacher);
        }, poolExecutor);
        CompletableFuture<Void> voidCompletableFuture2 = listTeacherFuture.thenAcceptAsync((res) -> {
            teacherList.addAll(res);
        }, poolExecutor);

        try {
            CompletableFuture.allOf(voidCompletableFuture1, voidCompletableFuture2).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return Result.ok().data("eduList", eduList).data("teacherList", teacherList);
    }
}
