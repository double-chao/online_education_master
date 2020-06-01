package com.lcc.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcc.eduservice.entity.EduCourse;
import com.lcc.eduservice.entity.EduTeacher;
import com.lcc.eduservice.service.EduCourseService;
import com.lcc.eduservice.service.EduTeacherService;
import com.lcc.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(description = "前台讲师")
@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @ApiOperation("分页查询讲师")
    @PostMapping("/getTeacherFrontList/{current}/{size}")
    public Result getTeacherFrontList(@PathVariable long current, @PathVariable long size) {
        Page<EduTeacher> pageTeacher = new Page<>(current,size);
        Map<String,Object> map = teacherService.getTeacherFrontList(pageTeacher);
        return Result.ok().data(map);
    }

    @ApiOperation("讲师详情")
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public Result getTeacherFrontInfo(@PathVariable String teacherId) {
        //1 根据讲师id查询讲师基本信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);
        //2 根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);
        return Result.ok().data("teacher",eduTeacher).data("courseList",courseList);
    }
}












