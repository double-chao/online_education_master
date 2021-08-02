package com.lcc.eduservice.entity.vo.course;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author Administrator
 * @Description
 * @Date 2021/8/1  19:30
 */
@Data
public class CourseVO {

    @ApiModelProperty(value = "课程ID")
    private Integer id;

    @ApiModelProperty(value = "课程讲师ID")
    private Integer teacherId;

    @ApiModelProperty(value = "课程专业ID")
    private Integer subjectId;

    @ApiModelProperty(value = "课程专业父级ID")
    private Integer subjectParentId;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "销售数量")
    private Integer buyCount;

    @ApiModelProperty(value = "浏览数量")
    private Integer viewCount;

    @ApiModelProperty(value = "课程状态 0未发布  1已发布")
    private String status;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
}
