package com.lcc.eduservice.controller;

import com.lcc.eduservice.constant.TeacherLevelEnum;
import com.lcc.eduservice.entity.EduTeacher;
import com.lcc.eduservice.entity.vo.ObjectPageInfo;
import com.lcc.eduservice.entity.vo.teacher.TeacherQuery;
import com.lcc.eduservice.entity.vo.teacher.TeacherVO;
import com.lcc.eduservice.service.EduTeacherService;
import com.lcc.result.Result;
import com.lcc.servicebase.valid.AddGroup;
import com.lcc.servicebase.valid.UpdateGroup;
import com.lcc.vo.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author chaochao
 * @since 2020-05-25
 */
@Api(value = "讲师管理", tags = "讲师管理操作服务接口")  //接口文当中有说明
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
    @CacheEvict(value = {"teacherFront"}, key = "'getTeacherFrontList'")
    @DeleteMapping("{id}")
    public Result delTeacherById(@ApiParam(name = "id", value = "讲师id", required = true)
                                 @PathVariable Integer id) {
        return teacherService.removeById(id) ? Result.ok() : Result.fail();
    }

    @ApiOperation("批量逻辑删除逻辑删除讲师")
    @CacheEvict(value = {"teacherFront"}, key = "'getTeacherFrontList'")
    @DeleteMapping("/removeBatchTeacher")
    public Result removeBatchTeacher(@RequestBody Set<Integer> teacherIdSet) {
        return teacherService.removeByIds(teacherIdSet) ? Result.ok() : Result.fail();
    }

    @Deprecated
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

    @ApiOperation("讲师列表-分页")
    @PostMapping("list/{current}/{size}")
    public Result listTeacher(@RequestBody(required = false) TeacherQuery teacherQuery,
                              @PathVariable Integer current, @PathVariable Integer size) {
        PageVO pageVO = new PageVO(current, size);
        return teacherService.listTeacher(pageVO, teacherQuery);
    }

    @ApiOperation("添加讲师")
    @CacheEvict(value = {"teacherFront"}, key = "'getTeacherFrontList'")
    @PostMapping("/addTeacher")
    public Result addTeacher(@Validated({AddGroup.class}) @RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        return save ? Result.ok() : Result.fail();
    }

    @ApiOperation("根据id查询讲师信息")
    @GetMapping("/getTeacher/{id}")
    public Result getTeacher(@ApiParam(name = "id", value = "讲师id", required = true)
                             @PathVariable Integer id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        TeacherVO teacherVO = new TeacherVO();
        BeanUtils.copyProperties(eduTeacher,teacherVO);
        teacherVO.setLevel(TeacherLevelEnum.getNameByCode(eduTeacher.getLevel()));
        return Result.ok().data("eduTeacher", teacherVO);
    }

    @ApiOperation("更新讲师信息")
    @CacheEvict(value = {"teacherFront"}, key = "'getTeacherFrontList'")
    @PostMapping("/updateTeacher")
    public Result updateTeacher(@Validated({UpdateGroup.class}) @RequestBody TeacherVO teacherVO) {
        EduTeacher modifyEduTeacher = new EduTeacher();
        modifyEduTeacher.setId(teacherVO.getId());
        modifyEduTeacher.setName(teacherVO.getName());
        modifyEduTeacher.setSort(teacherVO.getSort());
        modifyEduTeacher.setLevel(TeacherLevelEnum.getCodeByName(teacherVO.getLevel()));
        modifyEduTeacher.setCareer(teacherVO.getCareer());
        modifyEduTeacher.setIntro(teacherVO.getIntro());
        modifyEduTeacher.setAvatar(teacherVO.getAvatar());
        boolean flag = teacherService.updateById(modifyEduTeacher);
        return flag ? Result.ok() : Result.fail();
    }

    @ApiOperation("excel导入讲师信息")
    @CacheEvict(value = {"teacherFront"}, key = "'getTeacherFrontList'")
    @PostMapping("/excelImportTeacher")
    public Result excelImportTeacher(MultipartFile file) {
        boolean flag = teacherService.importExcelTeacher(file, teacherService);
        return flag ? Result.ok() : Result.fail();
    }
}