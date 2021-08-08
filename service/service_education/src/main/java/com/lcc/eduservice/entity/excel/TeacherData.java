package com.lcc.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author Administrator
 * @Description 讲师excel导入数据信息
 * @Date 2021/4/16  21:29
 */
@Data
public class TeacherData {

    @ExcelProperty(value = "讲师名字", index = 0)
    private String name;

    @ExcelProperty(value = "讲师简介", index = 1)
    private String intro;

    @ExcelProperty(value = "讲师头衔", index = 2)
    private String level;
}
