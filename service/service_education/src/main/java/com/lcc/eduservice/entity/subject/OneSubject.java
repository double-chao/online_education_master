package com.lcc.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程一级分类
 * </p>
 *
 * @author chaochao
 * @since 2020-05-27
 */
@Data
public class OneSubject {

    private String id;
    private String title;

    //一个一级分类有多个二级分类
    private List<TwoSubject> children = new ArrayList<>();
}
