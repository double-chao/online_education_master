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
 * 专业面试记录表
 * </p>
 *
 * @author double-chao
 * @since 2021-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("or_professional_interview")
@ApiModel(value="ProfessionalInterview对象", description="专业面试记录表")
public class ProfessionalInterview implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "专业面试表主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "基础简历主键ID")
    private Integer resumeId;

    @ApiModelProperty(value = "专业面试阶段(1:专业面试1,2：专业面试2,3：专业面试3...)")
    private Boolean professionalPhase;

    @ApiModelProperty(value = "面试结论")
    private Boolean conclusion;

    @ApiModelProperty(value = "会议ID")
    private String meetingId;

    @ApiModelProperty(value = "会议链接")
    private String meetingLink;

    @ApiModelProperty(value = "面试方式(0：远程，1：线下)")
    private Boolean type;

    @ApiModelProperty(value = "版本(乐观锁)")
    private Boolean version;

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
