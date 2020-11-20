package com.lcc.eduorder.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: 一十六
 * @Description: 订单查询条件对象
 * @Date: 2020/6/5 22:13
 */
@Data
public class OrderQueryVo {

    @ApiModelProperty(value = "支付状态 0未支付 1已支付")
    private Integer status;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
