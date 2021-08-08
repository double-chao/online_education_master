package com.lcc.online.recruitment.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lcc.servicebase.valid.AddGroup;
import com.lcc.servicebase.valid.StringValue;
import com.lcc.servicebase.valid.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;

/**
 * @Author Administrator
 * @Description 简历的DTO对象
 * @Date 2021/5/23  12:53
 */
@Data
public class BasicResumeDTO {

    @ApiModelProperty(value = "基础简历表主键ID")
    @NotNull(message = "修改时id不能为空", groups = {UpdateGroup.class})
    @Null(message = "添加时id不能指定", groups = {AddGroup.class})
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "员工姓名")
    @NotEmpty(message = "员工姓名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String employeeName;

    @ApiModelProperty(value = "手机号码")
    @NotEmpty(message = "手机号码不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String phone;

    @ApiModelProperty(value = "身份证号")
    @NotEmpty(message = "身份证号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String idCard;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "性别(0 男，1 女，2 保密)")
    @StringValue(values = {"男", "女", "保密"}, groups = {AddGroup.class, UpdateGroup.class}, message = "性别输入有误")
    private String sex;

    @ApiModelProperty(value = "应聘岗位(1:java工程师，2:安卓工程师)")
    @StringValue(values = {"java工程师", "安卓工程师"}, groups = {AddGroup.class, UpdateGroup.class}, message = "应聘岗位输入有误")
    @NotEmpty(message = "应聘岗位不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String applyJob;

    @ApiModelProperty(value = "应聘岗位职级(1:D1(初级)，2:D2(中级)，3:D3(高级)，4:D4(资深)，5:D5(专家))")
    @StringValue(values = {"初级", "中级", "高级", "资深", "专家"}, groups = {AddGroup.class, UpdateGroup.class}, message = "应聘岗位职级输入有误")
    @NotEmpty(message = "应聘岗位职级不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String applyLevel;

    @ApiModelProperty(value = "期望薪资")
    private BigDecimal expectedSalary;

    @ApiModelProperty(value = "个人简介")
    private String personalIntroduction;

    @ApiModelProperty(value = "简历来源(1:网上招聘，2:供应商，3:内推)")
    @StringValue(values = {"网上招聘", "供应商", "内推"}, groups = {AddGroup.class, UpdateGroup.class}, message = "简历来源输入有误")
    private String resumeFrom;

    @ApiModelProperty(value = "简历附件url地址")
    @URL(message = "简历附件必须是一个合法的url地址", groups = {AddGroup.class, UpdateGroup.class})
    private String resumeUrl;

}
