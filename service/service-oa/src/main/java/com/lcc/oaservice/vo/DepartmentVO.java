package com.lcc.oaservice.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author Administrator
 * @Description
 * @Date 2021/1/13  17:21
 */
@Data
public class DepartmentVO {
    @ApiModelProperty(value = "部门ID")
    private Integer id;

    @ApiModelProperty(value = "所属上级")
    private Integer pid;

    @ApiModelProperty(value = "公司ID")
    private Integer companyId;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
}
