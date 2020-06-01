package com.lcc.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * <p>
 * 课程分类实体类  一级分类  二级分类
 * </p>
 *
 * @author chaochao
 * @since 2020-05-27
 */
@Data
public class SubjectData {

    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
