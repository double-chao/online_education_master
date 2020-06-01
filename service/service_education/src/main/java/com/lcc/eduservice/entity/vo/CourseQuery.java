package com.lcc.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: 一十六
 * @Description: 课程查询对象类
 * @Date: 2020/5/29 15:03
 */
@Data
public class CourseQuery {

    @ApiModelProperty(value = "课程名称,模糊查询")
    private String title;

    @ApiModelProperty(value = "课程是否发布")
    private String status;
}
