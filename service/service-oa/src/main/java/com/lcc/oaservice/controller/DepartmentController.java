package com.lcc.oaservice.controller;


import com.github.pagehelper.PageInfo;
import com.lcc.oaservice.dto.DepartmentQueryDTO;
import com.lcc.oaservice.entity.TDepartment;
import com.lcc.oaservice.service.DepartmentService;
import com.lcc.oaservice.vo.DepartmentVO;
import com.lcc.pojo.Page;
import com.lcc.result.Result;
import com.lcc.servicebase.valid.AddGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 部门 前端控制器
 * </p>
 *
 * @author double-chao
 * @since 2021-01-13
 */
@Api(tags = "部门")
@RestController
@RequestMapping("/oaservice/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation("后端管理-分页获取部门列表信息")
    @PostMapping("/listCompanyPageInfo")
    public Result listCompanyPageInfo(@RequestBody(required = false) DepartmentQueryDTO departmentQueryDTO, Page page) {
        PageInfo<DepartmentVO> departmentVOPageInfo = departmentService.listCompanyByCondition(departmentQueryDTO, page);
        return Result.ok().data("departmentVOPageInfo", departmentVOPageInfo);
    }

    @ApiOperation("添加部门")
    @PostMapping("/saveDepartment")
    public Result saveDepartment(@Validated({AddGroup.class}) @RequestBody TDepartment department) {
        boolean save = departmentService.save(department);
        return save ? Result.ok() : Result.ok();
    }
}

