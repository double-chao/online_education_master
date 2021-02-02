package com.lcc.oaservice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Administrator
 * @Description 公司查询的条件对象 前端传过来的
 * @Date 2021/1/13  11:50
 */
@Data
public class CompanyQueryDTO {

    @ApiModelProperty(value = "公司名称")
    private String name;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
