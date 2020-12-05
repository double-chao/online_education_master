package com.lcc.eduservice.entity.vo.subject;

import lombok.Data;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2020/7/30
 * @description 一级菜单科目信息
 */
@Data
public class OneSubjectVO {

    private Integer id; //id

    private String title; //一级科目菜单名称

    private Integer parentId; //一级菜单的父id 设置为0

    private List<TwoSubjectVO> twoSubjectVOList; //一个一级菜单包含多个二级菜单
}
