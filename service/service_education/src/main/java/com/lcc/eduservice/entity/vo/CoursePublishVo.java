package com.lcc.eduservice.entity.vo;

import lombok.Data;
/**
 * 课程发布显示信息
 * @author chaochao
 * @since 2020-05-26
 */
@Data
public class CoursePublishVo {
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}
