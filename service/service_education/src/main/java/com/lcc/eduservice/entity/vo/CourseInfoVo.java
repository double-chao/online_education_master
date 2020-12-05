package com.lcc.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
/**
 * 课程基本信息查询/添加对象类
 * @author chaochao
 * @since 2020-05-28
 */
@Data
public class CourseInfoVo {
    @ApiModelProperty(value = "课程ID")
    private Integer id;

    @ApiModelProperty(value = "课程讲师ID")
    private Integer teacherId;

    @ApiModelProperty(value = "课程所属类别ID")
    private Integer subjectId;

    @ApiModelProperty(value = "一级分类ID")
    private Integer subjectParentId;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "课程简介")
    private String description;
}
