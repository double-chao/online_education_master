package com.lcc.oaservice.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author Administrator
 * @Description 公司 返回给前端的
 * @Date 2021/1/13  12:13
 */
@Data
public class CompanyVO {
    @ApiModelProperty(value = "公司ID")
    private Integer id;

    @ApiModelProperty(value = "所属上级")
    private Integer pid;

    @ApiModelProperty(value = "公司名称")
    private String name;

    @ApiModelProperty(value = "公司简称")
    private String shortName;

    @ApiModelProperty(value = "公司简介")
    private String intro;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
}
