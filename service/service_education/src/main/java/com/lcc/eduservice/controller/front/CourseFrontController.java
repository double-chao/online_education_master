package com.lcc.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcc.eduservice.client.OrderClient;
import com.lcc.eduservice.entity.EduCourse;
import com.lcc.eduservice.entity.frontvo.CourseFrontVo;
import com.lcc.eduservice.entity.frontvo.CourseWebVo;
import com.lcc.eduservice.service.EduChapterService;
import com.lcc.eduservice.service.EduCourseService;
import com.lcc.eduservice.service.front.CourseFrontService;
import com.lcc.result.Result;
import com.lcc.vo.CourseOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

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
    @Autowired
    private CourseFrontService courseFrontService;

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
    public Result getFrontCourseInfo(@PathVariable Integer courseId, HttpServletRequest request) {
        return courseFrontService.getFrontCourseInfoByCourseId(courseId, request);
    }

    @ApiOperation("根据课程id查询-订单的课程信息")
    @PostMapping("/getOrderCourseInfo/{id}")
    public CourseOrder getOrderCourseInfo(@PathVariable Integer id) {
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
        CourseOrder courseOrder = new CourseOrder();
        BeanUtils.copyProperties(courseInfo, courseOrder);
        return courseOrder;
    }
}
