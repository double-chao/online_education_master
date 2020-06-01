package com.lcc.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcc.eduservice.entity.EduCourse;
import com.lcc.eduservice.entity.frontvo.CourseFrontVo;
import com.lcc.eduservice.service.EduCourseService;
import com.lcc.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: 一十六
 * @Description: 前台课程
 * @Date: 2020/6/1 20:29
 */
@Api(description = "前台课程")
@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @ApiOperation("分页查询课程")
    @PostMapping("/getCourseFrontList/{current}/{size}")
    public Result getCourseFrontList(@PathVariable long current, @PathVariable long size,
                                     @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> coursePage = new Page<>(current,size);
        Map<String,Object> map = courseService.getCourseFrontList(coursePage,courseFrontVo);
        return Result.ok().data(map);
    }

}
