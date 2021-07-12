package com.lcc.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * @Author Administrator
 * @Description 分页
 * @Date 2020/11/25  11:03
 */
@Getter
@ApiModel("分页参数")
public class PageVO {
    @ApiModelProperty("页码")
    private Integer page;
    @ApiModelProperty("页大小，默认20")
    private Integer size;

    public PageVO(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public void setPage(Integer page) {
        if (page == null || page <= 0) {
            page = 1;
        }
        this.page = page;
    }

    public void setSize(Integer size) {
        if (size == null || size <= 0) {
            size = 20;
        }
        this.size = size;
    }
}
