package com.lcc.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcc.eduservice.entity.EduTeacher;
import com.lcc.eduservice.entity.vo.TeacherQuery;
import com.lcc.eduservice.service.EduTeacherService;
import com.lcc.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author chaochao
 * @since 2020-05-25
 */
@Api(description = "讲师管理")  //接口文当中有说明
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin //解决跨域问题
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation("讲师列表")
    @GetMapping("/findAllTeacher")
    public Result getTeacherList() {
        List<EduTeacher> teacherList = teacherService.list(null);
        return Result.ok().data("teacherList", teacherList);
    }

    @ApiOperation("根据id逻辑删除讲师")
    @DeleteMapping("{id}")
    public Result delTeacherById(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id) {
        boolean b = teacherService.removeById(id);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation("根据条件查询讲师分页")
    @PostMapping("pageTeacherCondition/{current}/{size}")
    //使用@RequestBody(required = false)  前端传过来的成json格式数据封装成对象信息，false代表可以为空 ,且必须用post提交
    public Result pageTeacherCondition(@PathVariable long current, @PathVariable long size,
                                       @RequestBody(required = false) TeacherQuery teacherQuery) {
        // 分页  当前页，多少条
        Page<EduTeacher> pageTeacher = new Page<>(current, size);
        // 构建查询对象
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        // 查询语句条件拼接  相当于动态sql
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name); //双引号中的值为数据库语句中的字段名字
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        wrapper.orderByDesc("gmt_create"); //创建时间排序
        teacherService.page(pageTeacher, wrapper);
        long total = pageTeacher.getTotal(); //总记录条数
        List<EduTeacher> records = pageTeacher.getRecords(); //讲师集合对象
        return Result.ok().data("total", total).data("rows", records);
    }

    @ApiOperation("添加讲师")
    @PostMapping("/addTeacher")
    public Result addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if (save) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation("根据id查询讲师信息")
    @GetMapping("/getTeacher/{id}")
    public Result getTeacher(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        return Result.ok().data("eduTeacher", eduTeacher);
    }

    @ApiOperation("更新讲师信息")
    @PostMapping("/updateTeacher")
    public Result updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

}

