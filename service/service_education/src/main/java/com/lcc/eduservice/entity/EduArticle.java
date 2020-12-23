package com.lcc.eduservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.lcc.servicebase.valid.AddGroup;
import com.lcc.servicebase.valid.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 文章
 * </p>
 *
 * @author chaochao
 * @since 2020-09-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "EduArticle对象", description = "文章")
public class EduArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章ID")
    @NotNull(message = "修改时id不能为空", groups = {UpdateGroup.class})
    @Null(message = "添加时id不能指定", groups = {AddGroup.class})
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "文章类别id")
    @NotNull(message = "文章类别id不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer categoryId;

    @ApiModelProperty(value = "会员id")
    @NotNull(message = "会员id不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer memberId;

    @ApiModelProperty(value = "文章标题")
    @NotEmpty(message = "文章标题不能为空", groups = {UpdateGroup.class, AddGroup.class})
    private String title;

    @ApiModelProperty(value = "文章图片")
    @URL(message = "文章图片必须是一个合法的url地址", groups = {UpdateGroup.class, AddGroup.class})
    private String picture;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "浏览数")
    private Integer scanNumber;

    @ApiModelProperty(value = "文章状态 0未发布  1已发布")
    private Boolean status;

    @ApiModelProperty(value = "点赞数")
    private Integer thumbsUp;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}
