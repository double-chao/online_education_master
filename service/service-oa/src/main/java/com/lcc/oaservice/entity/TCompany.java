package com.lcc.oaservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.lcc.servicebase.valid.AddGroup;
import com.lcc.servicebase.valid.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 公司
 * </p>
 *
 * @author double-chao
 * @since 2021-01-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "TCompany对象", description = "公司")
public class TCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公司ID")
    @NotNull(message = "修改时id不能为空", groups = {UpdateGroup.class})
    @Null(message = "添加时id不能指定", groups = {AddGroup.class})
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "所属上级")
    @NotNull(message = "所属上级id不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer pid;

    @ApiModelProperty(value = "公司名称")
    @NotEmpty(message = "公司名称不能为空", groups = {UpdateGroup.class, AddGroup.class})
    private String name;

    @ApiModelProperty(value = "公司简称")
    @NotEmpty(message = "公司简称不能为空", groups = {UpdateGroup.class, AddGroup.class})
    private String shortName;

    @ApiModelProperty(value = "公司简介")
    private String intro;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
