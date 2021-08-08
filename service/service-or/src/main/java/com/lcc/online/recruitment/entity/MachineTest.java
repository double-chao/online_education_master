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
 * 简历的机考成绩记录表
 * </p>
 *
 * @author double-chao
 * @since 2021-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("or_machine_test")
@ApiModel(value="MachineTest对象", description="简历的机考成绩记录表")
public class MachineTest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "简历机考成绩记录表主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "简历基础表的主键ID")
    private Integer resumeId;

    @ApiModelProperty(value = "机考成绩")
    private String score;

    @ApiModelProperty(value = "机考结论(0:不通过，1:通过)")
    private Boolean conclusion;

    @ApiModelProperty(value = "机考是否有效(0:失效，1:有效)")
    private Boolean effective;

    @ApiModelProperty(value = "机考时间")
    private Date interviewTime;

    @ApiModelProperty(value = "机考的URL地址")
    private String url;

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
