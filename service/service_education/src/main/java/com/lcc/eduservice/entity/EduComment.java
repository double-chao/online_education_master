package com.lcc.eduservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.lcc.servicebase.valid.AddGroup;
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
 * 课程评论
 * </p>
 *
 * @author chaochao
 * @since 2020-06-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EduComment对象", description="评论")
public class EduComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "评论父id")
    private Integer parentId;

    @ApiModelProperty(value = "课程id")
    private Integer courseId;

    @ApiModelProperty(value = "课程标题")
    private String courseTitle;

    @ApiModelProperty(value = "讲师id")
    private Integer teacherId;

    @ApiModelProperty(value = "会员id")
    private Integer memberId;

    @ApiModelProperty(value = "会员昵称")
    private String nickname;

    @ApiModelProperty(value = "会员头像")
    private String avatar;

    @ApiModelProperty(value = "评论内容")
    @NotEmpty(message = "评论内容不能为空", groups = {AddGroup.class})
    private String content;

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
