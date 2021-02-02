package com.lcc.oaservice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Administrator
 * @Description
 * @Date 2021/1/13  17:21
 */
@Data
public class DepartmentQueryDTO {

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "公司ID") // 前端传过来公司ID,打算前端做一个下拉框（两级联动）显示所有公司
    private Integer companyId;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;

}
