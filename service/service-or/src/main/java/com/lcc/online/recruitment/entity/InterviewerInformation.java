package com.lcc.online.recruitment.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 面试官信息表
 * </p>
 *
 * @author double-chao
 * @since 2021-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("or_interviewer_information")
@ApiModel(value="InterviewerInformation对象", description="面试官信息表")
public class InterviewerInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "面试官信息表主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "面试官名字")
    private String interviewerName;

    @ApiModelProperty(value = "面试官所属部门")
    private String department;

    @ApiModelProperty(value = "面试官类型(1:资格面试官,2:专业1面试官,3:专业2面试官,4:专业3面试官,5:综合面试官)")
    private Boolean interviewerType;

    @ApiModelProperty(value = "面试官职级(1:D1(初级),2:D2(中级),3:D3(高级),4:D4(资深),5:D5(专家))")
    private Boolean interviewerLevel;

    @ApiModelProperty(value = "面试官状态(0:失效，1:有效，2:审批中)")
    private Boolean interviewerStatus;

    @ApiModelProperty(value = "版本(乐观锁)")
    private Integer version;

    @ApiModelProperty(value = "是否删除(0(false)未删除 1(true)已删除)")
    private Boolean deleted;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改人")
    private String modifiedBy;

    @ApiModelProperty(value = "修改时间")
    private Date modifiedTime;


}
