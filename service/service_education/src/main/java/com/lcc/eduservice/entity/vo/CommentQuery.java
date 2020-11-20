package com.lcc.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: 一十六
 * @Description: 评论
 * @Date: 2020/6/6 12:17
 */
@Data
public class CommentQuery {

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "开始时间",example = "2019-01-01 10:10:10")
    private String begin;

    @ApiModelProperty(value = "结束时间",example = "2019-01-01 10:10:10")
    private String end;



}
