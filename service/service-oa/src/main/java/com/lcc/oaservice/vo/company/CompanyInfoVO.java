package com.lcc.oaservice.vo.company;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Administrator
 * @Description 公司id，名称，父id
 * @Date 2021/2/23  15:55
 */
@Data
public class CompanyInfoVO {
    @ApiModelProperty(value = "公司ID")
    private Integer id;

    @ApiModelProperty(value = "所属上级")
    private Integer pid;

    @ApiModelProperty(value = "公司名称")
    private String name;
}
