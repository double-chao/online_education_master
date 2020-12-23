package com.lcc.eduservice.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Administrator
 * @Description 文章数据传输对象
 * @Date 2020/10/12  10:22
 */
@Data
public class ArticleDTO {

    @ApiModelProperty(value = "最新发表")
    private String lastPublish;

    @ApiModelProperty(value = "热点推荐")
    private String hot;

    @ApiModelProperty(value = "每日一博")
    private String everyDay;

    @ApiModelProperty(value = "文章种类id")
    private String categoryId;
}
