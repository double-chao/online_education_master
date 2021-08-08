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
 * 环节历史记录表
 * </p>
 *
 * @author double-chao
 * @since 2021-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("or_history_operation")
@ApiModel(value="HistoryOperation对象", description="环节历史记录表")
public class HistoryOperation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "历史记录表主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "基础简历表主键ID")
    private Integer resumeId;

    @ApiModelProperty(value = "操作类型")
    private String operationType;

    @ApiModelProperty(value = "操作名字")
    private String operationName;

    @ApiModelProperty(value = "是否删除(0(false)未删除 1(true)已删除)")
    private Integer deleted;

    @ApiModelProperty(value = "操作人")
    private String operationBy;

    @ApiModelProperty(value = "操作时间")
    private Date operationTime;

    @ApiModelProperty(value = "操作备注")
    private String operationNote;


}
