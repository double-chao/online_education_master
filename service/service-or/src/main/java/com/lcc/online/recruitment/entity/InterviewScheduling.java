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
 * 面试排班表
 * </p>
 *
 * @author double-chao
 * @since 2021-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("or_interview_scheduling")
@ApiModel(value="InterviewScheduling对象", description="面试排班表")
public class InterviewScheduling implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "面试排班表主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "基础简历主键ID")
    private Integer resumeId;

    @ApiModelProperty(value = "面试官表主键ID")
    private Integer interviewerInfomationId;

    @ApiModelProperty(value = "面试阶段")
    private Boolean interviewPhase;

    @ApiModelProperty(value = "面试语言")
    private Boolean interviewLanguage;

    @ApiModelProperty(value = "面试开始时间")
    private Date interviewStartTime;

    @ApiModelProperty(value = "面试结束时间")
    private Date intervieEndTime;

    @ApiModelProperty(value = "面试状态(0:未完成，1:已完成)")
    private Boolean interviewStatus;

    @ApiModelProperty(value = "确认(0：待确认，1：确认)")
    private Boolean confirm;

    @ApiModelProperty(value = "版本(乐观锁)")
    private Integer version;

    @ApiModelProperty(value = "是否删除(0(false)未删除 1(true)已删除)")
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifiedTime;


}
