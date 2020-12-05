package com.lcc.eduservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.lcc.servicebase.valid.AddGroup;
import com.lcc.servicebase.valid.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 文章内容
 * </p>
 *
 * @author chaochao
 * @since 2020-09-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EduArticleDescription对象", description="文章内容")
public class EduArticleDescription implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章描述ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "文章id")
    private Integer articleId;

    @ApiModelProperty(value = "文章内容")
    @NotEmpty(message = "文章内容不能为空", groups = {UpdateGroup.class, AddGroup.class})
    private String description;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    private Boolean isDelete;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
