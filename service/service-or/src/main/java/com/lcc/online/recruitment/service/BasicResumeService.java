package com.lcc.online.recruitment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lcc.online.recruitment.dto.BasicResumeDTO;
import com.lcc.online.recruitment.dto.ResumeQueryDTO;
import com.lcc.online.recruitment.entity.BasicResume;
import com.lcc.result.Result;
import com.lcc.servicebase.valid.AddGroup;
import com.lcc.servicebase.valid.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * <p>
 * 基础简历表 服务类
 * </p>
 *
 * @author double-chao
 * @since 2021-05-23
 */
@Api(tags = "简历操作服务类")
@RequestMapping("/online/recruitment/basic/resume")
public interface BasicResumeService extends IService<BasicResume> {

    @ApiOperation("简历列表展示")
    @PostMapping("/list")
    Result list(@RequestBody(required = false) ResumeQueryDTO resumeQueryDTO);

    @ApiOperation("根据简历id，获取基础简历信息")
    @GetMapping("/getById/{id}")
    Result getById(@ApiParam(name = "id", value = "简历id", required = true) @PathVariable Integer id);

    @ApiOperation("录入简历")
    @PostMapping("/save")
    Result save(@Validated({AddGroup.class}) @RequestBody BasicResumeDTO basicResumeDTO);

    @ApiOperation("更新简历")
    @PutMapping("/update")
    Result update(@Validated({UpdateGroup.class}) @RequestBody BasicResumeDTO basicResumeDTO);

    @ApiOperation("根据简历id，删除简历")
    @DeleteMapping("/deletedById/{id}")
    Result deletedById(@ApiParam(name = "id", value = "简历id", required = true) @PathVariable Integer id);

    @ApiOperation("根据简历id集合，批量删除简历")
    @DeleteMapping("/deletedByBatch")
    Result deletedByBatch(@RequestBody Set<Integer> idSet);
}
