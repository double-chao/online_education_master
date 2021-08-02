package com.lcc.eduservice.entity.vo.teacher;

import com.lcc.servicebase.valid.AddGroup;
import com.lcc.servicebase.valid.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * @Author Administrator
 * @Description
 * @Date 2021/8/1  13:24
 */
@Data
public class TeacherVO {

    @ApiModelProperty(value = "讲师ID")
    @NotNull(message = "修改时id不能为空", groups = {UpdateGroup.class})
    @Null(message = "添加时id不能指定", groups = {AddGroup.class})
    private Integer id;

    @ApiModelProperty(value = "讲师姓名")
    @NotEmpty(message = "讲师姓名不能为空", groups = {UpdateGroup.class, AddGroup.class})
    private String name;

    @ApiModelProperty(value = "讲师简介")
    private String intro;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "讲师资历,一句话说明讲师")
    private String career;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private String level;

    @ApiModelProperty(value = "讲师头像")
    @URL(message = "头像必须是一个合法的url地址", groups = {UpdateGroup.class, AddGroup.class})
    private String avatar;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
}
