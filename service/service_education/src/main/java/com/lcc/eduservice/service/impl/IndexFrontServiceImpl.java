package com.lcc.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lcc.eduservice.entity.EduCourse;
import com.lcc.eduservice.entity.EduTeacher;
import com.lcc.eduservice.service.EduCourseService;
import com.lcc.eduservice.service.EduTeacherService;
import com.lcc.eduservice.service.IndexFrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author Administrator
 * @Description 首页
 * @Date 2020/11/21  18:18
 */
@Service
public class IndexFrontServiceImpl implements IndexFrontService {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private ThreadPoolExecutor poolExecutor;

    @Override
    public Map getHotCourseAndTeacher() {
        Map<String, List> map = new HashMap<>();
        List<EduCourse> eduList = new ArrayList<>();
        List<EduTeacher> teacherList = new ArrayList<>();
        CompletableFuture<List<EduCourse>> listCourseFuture = CompletableFuture.supplyAsync(() -> {
            QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
            wrapper.eq("status", "Normal");
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
            map.put("eduList", eduList);
            map.put("teacherList", teacherList);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return map;
    }
}
