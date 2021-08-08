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
 * 简历环节表
 * </p>
 *
 * @author double-chao
 * @since 2021-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("or_link")
@ApiModel(value="Link对象", description="简历环节表")
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "简历环节表主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "基础简历表主键ID")
    private Integer resumId;

    @ApiModelProperty(value = "环节名称(枚举值,详情请看数据库设计值)")
    private Boolean linkName;

    @ApiModelProperty(value = "环节状态(枚举值,详情请看数据库设计值)")
    private Boolean linkStatus;

    @ApiModelProperty(value = "环节版本")
    private String linkVersion;

    @ApiModelProperty(value = "是否有效(0:无效，1:有效，2:待确认)")
    private Boolean effective;

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
