package com.lcc.oaservice.controller;


import com.github.pagehelper.PageInfo;
import com.lcc.oaservice.dto.CompanyQueryDTO;
import com.lcc.oaservice.entity.TCompany;
import com.lcc.oaservice.service.CompanyService;
import com.lcc.oaservice.vo.CompanyVO;
import com.lcc.result.Result;
import com.lcc.servicebase.valid.AddGroup;
import com.lcc.servicebase.valid.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * <p>
 * 公司 前端控制器
 * </p>
 *
 * @author double-chao
 * @since 2021-01-13
 */
@Api(tags = "公司")
@RestController
@RequestMapping("/oaservice/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @ApiOperation("后端管理-分页获取公司列表信息")
    @PostMapping("/listCompanyPageInfo/{current}/{size}")
    public Result listCompanyPageInfo(@RequestBody(required = false) CompanyQueryDTO companyQueryDTO,
                                      @PathVariable Integer current, @PathVariable Integer size) {
        PageInfo<CompanyVO> companyVOPageInfo = companyService.listCompanyByCondition(companyQueryDTO, current, size);
        return Result.ok().data("companyVOPageInfo", companyVOPageInfo);
    }

    @ApiOperation("公司id获取公司信息")
    @GetMapping("/getCompanyById/{id}")
    public Result getCompanyById(@ApiParam(name = "id", value = "公司id", required = true) @PathVariable Integer id) {
        TCompany company = companyService.getById(id);
        return Result.ok().data("company", company);
    }

    @ApiOperation("添加公司")
    @PostMapping("/insertCompany")
    public Result insertCompany(@Validated({AddGroup.class}) @RequestBody TCompany company) {
        boolean save = companyService.save(company);
        return save ? Result.ok() : Result.fail();
    }

    @ApiOperation("根据公司id逻辑删除公司")
    @DeleteMapping("/{id}")
    public Result deleteCompany(@ApiParam(name = "id", value = "公司id", required = true) @PathVariable Integer id) {
        boolean b = companyService.removeById(id);
        return b ? Result.ok() : Result.fail();
    }

    @ApiOperation(value = "根据id列表-批量删除公司")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody Set<Integer> idSets) {
        boolean b = companyService.removeByIds(idSets);
        return b ? Result.ok() : Result.fail();
    }

    @ApiOperation(value = "更新公司")
    @PostMapping("/updateCompany")
    public Result updateCompany(@Validated({UpdateGroup.class}) @RequestBody TCompany company) {
        boolean b = companyService.updateById(company);
        return b ? Result.ok() : Result.fail();
    }

}

