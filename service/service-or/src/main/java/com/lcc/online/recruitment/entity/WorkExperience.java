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
 * 工作经历表
 * </p>
 *
 * @author double-chao
 * @since 2021-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("or_work_experience")
@ApiModel(value="WorkExperience对象", description="工作经历表")
public class WorkExperience implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "工作经历表主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "基础简历表的主键ID")
    private Integer resumeId;

    @ApiModelProperty(value = "工作开始时间")
    private Date startTime;

    @ApiModelProperty(value = "工作结束时间")
    private Date endTime;

    @ApiModelProperty(value = "所在公司")
    private String company;

    @ApiModelProperty(value = "工作经验描述")
    private String workExperience;

    @ApiModelProperty(value = "版本(乐观锁)")
    private Integer version;

    @ApiModelProperty(value = "是否删除( 逻辑删除  0(false)未删除  1(true)已删除)")
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifiedTime;


}
