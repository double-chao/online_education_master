package com.lcc.online.recruitment.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 基础简历表
 * </p>
 *
 * @author double-chao
 * @since 2021-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("or_basic_resume")
@ApiModel(value = "BasicResume对象", description = "基础简历表")
public class BasicResume implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "基础简历表主键ID")
    @TableId(value = "id", type = IdType.AUTO)
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

    @ApiModelProperty(value = "性别(0 男，1 女，2 保密)")
    private Byte sex;

    @ApiModelProperty(value = "应聘岗位(1:java工程师，2:安卓工程师)")
    private Byte applyJob;

    @ApiModelProperty(value = "应聘岗位职级(1:D1(初级)，2:D2(中级)，3:D3(高级)，4:D4(资深)，5:D5(专家))")
    private Byte applyLevel;

    @ApiModelProperty(value = "期望薪资")
    private BigDecimal expectedSalary;

    @ApiModelProperty(value = "个人简介")
    private String personalIntroduction;

    @ApiModelProperty(value = "简历来源(1:网上招聘，2:供应商，3:内推)")
    private Byte resumeFrom;

    @ApiModelProperty(value = "简历状态(1:释放，2:未释放，3:例外)")
    private Byte resumeStatus;

    @ApiModelProperty(value = "简历附件url地址")
    private String resumeUrl;

    @ApiModelProperty(value = "是否删除( 逻辑删除 0(false)未删除 1(true)已删除)")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "版本(乐观锁)")
    @Version
    private Integer version;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT) // 添加时 自动填充注解  要配合MyMetaObjectHandler实现接口一起使用
    private Date createTime;

    @ApiModelProperty(value = "修改人")
    private String modifiedBy;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE) //添加、更新  自动填充注解
    private Date modifiedTime;

}
