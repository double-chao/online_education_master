package com.lcc.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Administrator
 * @Description 轮播图查询
 * @Date 2020/8/28
 */
@Data
public class BannerQuery {
    @ApiModelProperty(value = "标题,模糊查询")
    private String title;

    @ApiModelProperty(value = "是否显示 0显示 1不显示")
    private boolean status;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
