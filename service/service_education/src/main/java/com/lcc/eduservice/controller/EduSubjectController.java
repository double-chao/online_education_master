package com.lcc.eduservice.controller;


import com.lcc.eduservice.entity.subject.OneSubject;
import com.lcc.eduservice.service.EduSubjectService;
import com.lcc.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author chaochao
 * @since 2020-05-27
 */
@Api(description = "课程分类")
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @ApiOperation("添加课程分类导入excel文件")
    @PostMapping("/addSubject")
    public Result addSubject(MultipartFile file){
        subjectService.saveSubjectFile(file,subjectService);
        return Result.ok();
    }

    @ApiOperation("课程分类列表")
    @GetMapping("/getSubjectList")
    public Result getSubjectList(){
        List<OneSubject> allList = subjectService.getAllOneTwoSubject();
        return Result.ok().data("list",allList);
    }

}

