package com.lcc.eduservice.entity.vo.subject;

import lombok.Data;

/**
 * @Author Administrator
 * @Date 2020/7/30
 * @description 二级菜单科目信息
 */
@Data
public class TwoSubjectVO {

    private String id; //id

    private String title; //二级菜单科目名称

    private String parentId; // 二级菜单的父id为一级菜单的id

}
