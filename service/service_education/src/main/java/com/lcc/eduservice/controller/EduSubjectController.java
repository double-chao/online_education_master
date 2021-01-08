package com.lcc.eduservice.controller;


import com.lcc.eduservice.entity.EduSubject;
import com.lcc.eduservice.entity.vo.subject.OneSubjectVO;
import com.lcc.eduservice.service.EduSubjectService;
import com.lcc.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

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
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @ApiOperation("添加课程分类导入excel文件")
//    @PreAuthorize("@el.check('subject.import')")
    @PostMapping("/addSubject")
    public Result addSubject(MultipartFile file) {
        subjectService.saveSubjectFile(file, subjectService);
        return Result.ok();
    }

    @ApiOperation("获取科目菜单信息")
//    @PreAuthorize("@el.check('subject.list')")
    @GetMapping("/getAllSubject")
    public Result getAllSubject() {
        List<OneSubjectVO> subjectVOList = subjectService.getSubjectInfo();
        return Result.ok().data("subjectInfo", subjectVOList);
    }

    @ApiOperation("添加一级科目菜单")
    @PostMapping("/insertOneSubject")
    public Result insertOneSubject(@RequestBody EduSubject eduSubject) {
        boolean b = subjectService.saveOneSubject(eduSubject);
        return b ? Result.ok() : Result.fail().message("课程科目已经存在!");
    }

    @ApiOperation("添加二级科目菜单")
    @PostMapping("/insertTwoSubject/{id}/{sort}/{title}")
    public Result insertTwoSubject(@PathVariable Integer id, @PathVariable Integer sort,
                                   @PathVariable String title) { //传过来的id为一级菜单的id
        boolean b = subjectService.saveTwoSubjectById(id, sort, title);
        return b ? Result.ok() : Result.fail().message("添加二级课程科目失败");
    }

    @ApiOperation("批量删除")
    @DeleteMapping("/deleteRows")
    public Result deleteRows(@RequestBody Set<Integer> idSets){
        subjectService.removeByIds(idSets);
        return Result.ok();
    }

}

