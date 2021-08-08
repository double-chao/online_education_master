package com.lcc.online.recruitment.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author Administrator
 * @Description 简历条件查询DTO
 * @Date 2021/6/6  18:59
 */
@Data
public class ResumeQueryDTO {

    @ApiModelProperty(value = "基础简历表主键ID", hidden = true)
    private Integer id;

    @ApiModelProperty(value = "简历编码")
    private String resumeCode;

    @ApiModelProperty(value = "员工姓名")
    private String employeeName;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "应聘岗位(1:java工程师，2:安卓工程师)")
    private String applyJob;

    @ApiModelProperty(value = "应聘岗位职级(1:D1(初级)，2:D2(中级)，3:D3(高级)，4:D4(资深)，5:D5(专家))")
    private String applyLevel;

    @ApiModelProperty(value = "简历来源(1:网上招聘，2:供应商，3:内推)")
    private String resumeFrom;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty("页码")
    private Integer page = 1;

    @ApiModelProperty("页大小，默认10")
    private Integer size = 10;
}
