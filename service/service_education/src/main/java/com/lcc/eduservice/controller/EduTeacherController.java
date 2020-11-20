package com.lcc.eduservice.controller;

import com.lcc.eduservice.entity.EduTeacher;
import com.lcc.eduservice.entity.vo.ObjectPageInfo;
import com.lcc.eduservice.entity.vo.TeacherQuery;
import com.lcc.eduservice.service.EduTeacherService;
import com.lcc.result.Result;
import com.lcc.servicebase.valid.AddGroup;
import com.lcc.servicebase.valid.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.validation.annotation.Validated;
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
//@CrossOrigin //解决跨域问题
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
//    @PreAuthorize("@el.check('teacher.remove')")
    @CacheEvict(value = {"teacherFront"}, key = "'getTeacherFrontList'")
    @DeleteMapping("{id}")
    public Result delTeacherById(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id) {
        return teacherService.removeById(id) ? Result.ok() : Result.fail();
    }

    @ApiOperation("根据条件查询讲师分页")
    @PostMapping("pageTeacherCondition/{current}/{size}")
    //使用@RequestBody(required = false)  前端传过来的成json格式数据封装成对象信息，false代表可以为空 ,且必须用post提交
    public Result pageTeacherCondition(@PathVariable long current, @PathVariable long size,
                                       @RequestBody(required = false) TeacherQuery teacherQuery) {
        ObjectPageInfo teacherPageInfo = teacherService.selectAllTeacherPageInfo(current, size, teacherQuery);
        long total = teacherPageInfo.getTotal(); //总记录条数
        List<EduTeacher> records = teacherPageInfo.getList(); //讲师集合对象
        return Result.ok().data("total", total).data("rows", records);
    }

    @ApiOperation("添加讲师")
//    @PreAuthorize("@el.check('teacher.add')")
    @CacheEvict(value = {"teacherFront"}, key = "'getTeacherFrontList'")
    @PostMapping("/addTeacher")
    public Result addTeacher(@Validated({AddGroup.class}) @RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        return save ? Result.ok() : Result.fail();
    }

    @ApiOperation("根据id查询讲师信息")
    @GetMapping("/getTeacher/{id}")
    public Result getTeacher(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        return Result.ok().data("eduTeacher", eduTeacher);
    }

    @ApiOperation("更新讲师信息")
    @CacheEvict(value = {"teacherFront"}, key = "'getTeacherFrontList'")
    @PostMapping("/updateTeacher")
    public Result updateTeacher(@Validated({UpdateGroup.class}) @RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        return flag ? Result.ok() : Result.fail();
    }

}

