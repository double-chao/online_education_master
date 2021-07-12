package com.lcc.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lcc.eduservice.entity.EduTeacher;
import com.lcc.eduservice.entity.excel.TeacherData;
import com.lcc.eduservice.service.EduTeacherService;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.servicebase.exceptionhandler.CodeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

/**
 * @Author Administrator
 * @Description 讲师信息excel监听类
 * @Date 2021/4/16  21:28
 */
public class TeacherExcelListener extends AnalysisEventListener<TeacherData> {

    private EduTeacherService teacherService;

    public TeacherExcelListener() {
    }

    public TeacherExcelListener(EduTeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Override
    public void invoke(TeacherData teacherData, AnalysisContext context) {
        if (ObjectUtils.isEmpty(teacherData)) {
            throw new BadException(CodeEnum.IMPORT_EXCEL_TEACHER_NULL_EXCEPTION.getCode(),
                    CodeEnum.IMPORT_EXCEL_TEACHER_NULL_EXCEPTION.getMsg());
        }
        EduTeacher teacher = new EduTeacher();
        BeanUtils.copyProperties(teacherData, teacher);
        teacherService.save(teacher);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
