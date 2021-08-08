package com.lcc.online.recruitment.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author Administrator
 * @Description 简历返回信息
 * @Date 2021/5/23  14:39
 */
@Data
public class BasicResumeVO {

    @ApiModelProperty(value = "基础简历表主键ID")
    private Integer id;

    @ApiModelProperty(value = "简历编码")
    private String resumeCode;

    @ApiModelProperty(value = "员工姓名")
    private String employeeName;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "应聘岗位")
    private String applyJob;

    @ApiModelProperty(value = "应聘岗位职级")
    private String applyLevel;

    @ApiModelProperty(value = "期望薪资")
    private BigDecimal expectedSalary;

    @ApiModelProperty(value = "个人简介")
    private String personalIntroduction;

    @ApiModelProperty(value = "简历来源")
    private String resumeFrom;

    @ApiModelProperty(value = "简历附件url地址")
    private String resumeUrl;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改人")
    private String modifiedBy;

    @ApiModelProperty(value = "更新时间")
    private Date modifiedTime;
}
