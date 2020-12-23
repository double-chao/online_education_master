package com.lcc.eduservice.controller;


import com.lcc.eduservice.entity.EduCourse;
import com.lcc.eduservice.entity.vo.CourseInfoVo;
import com.lcc.eduservice.entity.vo.CoursePublishVo;
import com.lcc.eduservice.entity.vo.CourseQuery;
import com.lcc.eduservice.entity.vo.ObjectPageInfo;
import com.lcc.eduservice.service.EduCourseService;
import com.lcc.result.Result;
import com.lcc.security.annonation.AnonymousAccess;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author chaochao
 * @since 2020-05-28
 */
@Api(description = "课程基本信息")
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @ApiOperation("课程列表")
    @GetMapping("/getCourseList")
    public Result getCourseList() {
        List<EduCourse> list = courseService.list(null);
        return Result.ok().data("list",list);
    }

    @ApiOperation("根据条件查询课程分页")
    @PostMapping("/pageCourseCondition/{current}/{size}")
    //使用@RequestBody(required = false)  前端传过来的成json格式数据封装成对象信息，false代表可以为空 ,且必须用post提交
    public Result pageCourseCondition(@PathVariable long current, @PathVariable long size,
                                       @RequestBody(required = false) CourseQuery courseQuery) {
        ObjectPageInfo pageInfo = courseService.selectAllCoursePageInfo(current, size, courseQuery);
        long total = pageInfo.getTotal();
        List<EduCourse> records = pageInfo.getList();
        return Result.ok().data("total", total).data("rows", records);
    }

    @ApiOperation("添加课程基本信息")
    @AnonymousAccess
    @PostMapping("/addCourseInfo")
    public Result addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        Integer id = courseService.saveCourseInfo(courseInfoVo);
        return Result.ok().data("courseId",id);
    }

    @ApiOperation("根据id得到课程信息")
    @GetMapping("/getCourseInfoById/{courseId}")
    public Result getCourseInfoById(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable Integer courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return Result.ok().data("courseInfoVo",courseInfoVo);
    }

    @ApiOperation("更新课程信息")
    @PostMapping("/updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return Result.ok();
    }

    @ApiOperation("根据课程id得到发布课程信息")
    @GetMapping("/getPublishVoInfo/{courseId}")
    public Result getPublishVoInfo(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable Integer courseId){
        CoursePublishVo publishVoInfo = courseService.getPublishVoInfo(courseId);
        return Result.ok().data("publishVoInfo",publishVoInfo);
    }

    @ApiOperation("最终发布")
    @CacheEvict(value = {"courseFront"}, key = "'getCourseFrontList'")
    @PostMapping("/publishCourse/{courseId}")
    public Result publishCourse(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable Integer courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal"); //状态设置为已发布
        courseService.updateById(eduCourse);
        return Result.ok();
    }

    @ApiOperation("删除课程")
    @DeleteMapping("{courseId}")
    public Result deleteCourse(@PathVariable Integer courseId) {
        courseService.removeCourse(courseId);
        return Result.ok();
    }
}

