package com.lcc.eduservice.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: 一十六
 * @Description: 分页封装对象
 * @Date: 2020/5/29 15:10
 */
@Data
public class ObjectPageInfo {
    private long total;
    private List list;
}
