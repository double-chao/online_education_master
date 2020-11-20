package com.lcc.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lcc.eduservice.entity.EduSubject;
import com.lcc.eduservice.entity.excel.SubjectData;
import com.lcc.eduservice.service.EduSubjectService;
import com.lcc.servicebase.exceptionhandler.BadException;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 读取excel文件监听类
 * </p>
 *
 * @author chaochao
 * @since 2020-05-27
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    //不能实现数据库操作
    //SubjectExcelListener不能交给spring进行管理，需要自己new，不能注入其他对象
    public EduSubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * 读取excel内容，一行一行进行读取
     *
     * @param subjectData
     * @param analysisContext
     */
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (StringUtils.isEmpty(subjectData)) {
            throw new BadException(20001, "文件数据为空");
        }
        //一行一行读取，每次读取有两个值，第一个值一级分类，第二个值二级分类
        //判断一级分类是否重复
        EduSubject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if (StringUtils.isEmpty(existOneSubject)) { //没有相同一级分类，进行添加
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());//一级分类名称
            subjectService.save(existOneSubject); //一级分类保存到数据库中
        }
        //获取一级分类id值
        String pid = existOneSubject.getId();
        //添加二级分类
        //判断二级分类是否重复
        EduSubject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if (StringUtils.isEmpty(existTwoSubject)) {
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());//二级分类名称
            subjectService.save(existTwoSubject); //二级分类保存到数据库中
        }
    }

    /**
     * 判断一级分类不能重复添加
     *
     * @param subjectService
     * @param name
     * @return
     */
    private EduSubject existOneSubject(EduSubjectService subjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        EduSubject oneSubject = subjectService.getOne(wrapper); //数据库中查询一级分类是否存在
        return oneSubject;
    }

    /**
     * 判断二级分类不能重复添加
     *
     * @param subjectService
     * @param name
     * @param pid
     * @return
     */
    private EduSubject existTwoSubject(EduSubjectService subjectService, String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject twoSubject = subjectService.getOne(wrapper); //数据库中查询二级分类是否存在
        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

}
