package com.lcc.eduservice.controller.front;

import com.lcc.eduservice.service.EduCourseService;
import com.lcc.eduservice.service.EduTeacherService;
import com.lcc.eduservice.service.IndexFrontService;
import com.lcc.result.Result;
import com.lcc.security.annonation.AnonymousAccess;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@Api(description = "首页热门课程名师展示")
@RestController
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {
    @Autowired
    private IndexFrontService indexFrontService;

    @ApiOperation("首页课程、名师热门信息")
    @AnonymousAccess
    @GetMapping("/index")
    @Cacheable(value = {"courseAndTeacherHot"}, key = "#root.method.name")
    public Result index() {
        Map map = indexFrontService.getHotCourseAndTeacher();
        return Result.ok().data("eduList", map.get("eduList")).data("teacherList", map.get("teacherList"));
    }
}
