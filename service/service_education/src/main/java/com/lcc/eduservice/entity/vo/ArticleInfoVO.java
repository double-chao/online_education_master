package com.lcc.eduservice.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lcc.servicebase.valid.AddGroup;
import com.lcc.servicebase.valid.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author Administrator
 * @Description 文章VO类
 * @Date 2020/9/10
 */
@Data
public class ArticleInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章ID")
    @NotNull(message = "修改时id不能为空", groups = {UpdateGroup.class})
    @Null(message = "添加时id不能指定", groups = {AddGroup.class})
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "会员id")
    @NotNull(message = "会员id不能为空",groups = {AddGroup.class,UpdateGroup.class})
    private Integer memberId;

    @ApiModelProperty(value = "文章类别ID")
    @NotNull(message = "文章类别ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer categoryId;

    @ApiModelProperty(value = "文章标题")
    @NotEmpty(message = "文章标题不能为空", groups = {UpdateGroup.class, AddGroup.class})
    private String title;

    @ApiModelProperty(value = "文章图片")
    @URL(message = "文章图片必须是一个合法的url地址", groups = {UpdateGroup.class, AddGroup.class})
    private String picture;

    @ApiModelProperty(value = "文章内容")
    @NotEmpty(message = "文章内容不能为空", groups = {UpdateGroup.class, AddGroup.class})
    private String description;

    @ApiModelProperty(value = "浏览数")
    private Integer scanNumber;

    @ApiModelProperty(value = "点赞数")
    private Integer thumbsUp;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;

    @ApiModelProperty(value = "会员昵称")
    private String nickname;

    @ApiModelProperty(value = "会员头像")
    private String avatar;
}
