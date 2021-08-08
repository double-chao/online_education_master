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
 * 当前简历所处的环节SLA表
 * </p>
 *
 * @author double-chao
 * @since 2021-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("or_sla")
@ApiModel(value="Sla对象", description="当前简历所处的环节SLA表")
public class Sla implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "SLA表的主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "基础简历表的主键ID")
    private Integer resumeId;

    @ApiModelProperty(value = "sla的名称")
    private Boolean slaName;

    @ApiModelProperty(value = "sla状态")
    private Boolean slaStatus;

    @ApiModelProperty(value = "sla版本")
    private String slaVersion;

    @ApiModelProperty(value = "是否有效(0:无效，1:有效，2:待确认)")
    private Boolean effective;

    @ApiModelProperty(value = "关键环节状态(0:初始，1:可开启综合面试，2:可开启租用)")
    private Boolean keyLinkStatus;

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
