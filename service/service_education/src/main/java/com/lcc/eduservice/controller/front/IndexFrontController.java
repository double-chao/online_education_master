package com.lcc.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lcc.eduservice.entity.EduCourse;
import com.lcc.eduservice.entity.EduTeacher;
import com.lcc.eduservice.service.EduCourseService;
import com.lcc.eduservice.service.EduTeacherService;
import com.lcc.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description = "首页热门课程名师展示")
@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;

    //查询前8条热门课程，查询前4条名师
    @ApiOperation("首页课程、名师热门信息")
    @GetMapping("/index")
    public Result index() {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");  //查询前8条热门课程
        List<EduCourse> eduList = courseService.list(wrapper);

        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");  //查询前4条名师
        List<EduTeacher> teacherList = teacherService.list(wrapperTeacher);

        return Result.ok().data("eduList",eduList).data("teacherList",teacherList);
    }

}
