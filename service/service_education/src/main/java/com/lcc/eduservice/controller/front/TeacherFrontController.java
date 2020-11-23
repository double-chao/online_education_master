package com.lcc.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcc.eduservice.entity.EduCourse;
import com.lcc.eduservice.entity.EduTeacher;
import com.lcc.eduservice.service.EduCourseService;
import com.lcc.eduservice.service.EduTeacherService;
import com.lcc.result.Result;
import com.lcc.security.annonation.AnonymousAccess;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@Api(description = "前台讲师")
@RestController
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private ThreadPoolExecutor poolExecutor;

    @ApiOperation("分页查询讲师")
    @AnonymousAccess
    @PostMapping("/getTeacherFrontList/{current}/{size}")
    public Result getTeacherFrontList(@PathVariable long current, @PathVariable long size) {
        Page<EduTeacher> pageTeacher = new Page<>(current, size);
        Map<String, Object> map = teacherService.getTeacherFrontList(pageTeacher);
        return Result.ok().data(map);
    }

    @ApiOperation("讲师详情")
    @AnonymousAccess
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public Result getTeacherFrontInfo(@PathVariable String teacherId) {
        EduTeacher eduTeacher = new EduTeacher();
        CompletableFuture<Void> runAsyncTeacher = CompletableFuture.runAsync(() -> {
            //1 根据讲师id查询讲师基本信息
            EduTeacher eduTeacherGet = teacherService.getById(teacherId);
            BeanUtils.copyProperties(eduTeacherGet, eduTeacher);
        }, poolExecutor);

        List<EduCourse> courseList = new ArrayList<>();
        CompletableFuture<Void> runAsyncCourse = CompletableFuture.runAsync(() -> {
            //2 根据讲师id查询所讲课程
            QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
            wrapper.eq("teacher_id", teacherId);
            List<EduCourse> courseListGet = courseService.list(wrapper);
            courseList.addAll(courseListGet);
        }, poolExecutor);
        try {
            CompletableFuture.allOf(runAsyncTeacher, runAsyncCourse).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return Result.ok().data("teacher", eduTeacher).data("courseList", courseList);
    }
}












