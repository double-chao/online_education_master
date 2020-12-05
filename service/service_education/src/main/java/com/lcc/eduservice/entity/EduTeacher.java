package com.lcc.eduservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.lcc.servicebase.valid.AddGroup;
import com.lcc.servicebase.valid.ListValue;
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
 * 讲师实体
 * </p>
 *
 * @author chaochao
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "EduTeacher对象", description = "讲师")
public class EduTeacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "讲师ID")
    @NotNull(message = "修改时id不能为空", groups = {UpdateGroup.class})
    @Null(message = "添加时id不能指定", groups = {AddGroup.class})
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "讲师姓名")
    @NotEmpty(message = "讲师姓名不能为空", groups = {UpdateGroup.class, AddGroup.class})
    private String name;

    @ApiModelProperty(value = "讲师姓名反转,模糊查询")
    private String reverseName;

    @ApiModelProperty(value = "讲师简介")
//    @Pattern(regexp = "^[a-zA-Z$]") 正则表达式校验，----->在前端js中正则表达式:/^[a-zA-Z$]/
    private String intro;

    @ApiModelProperty(value = "讲师资历,一句话说明讲师")
    private String career;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    @ListValue(values = {1, 2}, groups = {AddGroup.class}) //自定义注解，校验：添加时值只能为1,2
    private Integer level;

    @ApiModelProperty(value = "讲师头像")
    @URL(message = "头像必须是一个合法的url地址", groups = {UpdateGroup.class, AddGroup.class})
    private String avatar;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic //逻辑删除注解
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT) // 添加时 自动填充注解  要配合MyMetaObjectHandler实现接口一起使用
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE) //添加、更新  自动填充注解
    private Date gmtModified;

}
