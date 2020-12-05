package com.lcc.eduservice.entity.frontvo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseFrontVo {

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "讲师id")
    private Integer teacherId;

    @ApiModelProperty(value = "一级类别id")
    private Integer subjectParentId;

    @ApiModelProperty(value = "二级类别id")
    private Integer subjectId;

    @ApiModelProperty(value = "销量排序")
    private Integer buyCountSort;

    @ApiModelProperty(value = "最新时间排序")
    private String gmtCreateSort;

    @ApiModelProperty(value = "价格排序")
    private Integer priceSort;
}
