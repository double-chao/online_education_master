package com.lcc.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcc.eduservice.entity.EduSubject;
import com.lcc.eduservice.entity.excel.SubjectData;
import com.lcc.eduservice.entity.subject.OneSubject;
import com.lcc.eduservice.entity.subject.TwoSubject;
import com.lcc.eduservice.listener.SubjectExcelListener;
import com.lcc.eduservice.mapper.EduSubjectMapper;
import com.lcc.eduservice.service.EduSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author chaochao
 * @since 2020-05-27
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubjectFile(MultipartFile file,EduSubjectService subjectService) {
        try (InputStream inputStream = file.getInputStream()) {
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", "0");
        List<EduSubject> eduSubjectListOne = baseMapper.selectList(queryWrapper);

        QueryWrapper<EduSubject> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.ne("parent_id", "0");
        List<EduSubject> eduSubjectListTwo = baseMapper.selectList(queryWrapper1);

        List<OneSubject> oneSubjectList = new ArrayList<>(eduSubjectListOne.size());
        // 一级菜单
        for (int i = 0; i < eduSubjectListOne.size(); i++) {
            EduSubject eduSubject = eduSubjectListOne.get(i);
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject, oneSubject);
            oneSubjectList.add(oneSubject);

            List<TwoSubject> twoSubjectList = new ArrayList<>(eduSubjectListTwo.size());
            //二级菜单
            for (int j = 0; j < eduSubjectListTwo.size(); j++) {
                EduSubject eduSubject2 = eduSubjectListTwo.get(j);
                if (eduSubject2.getParentId().equals(eduSubject.getId())) { //判断二级菜单的父id与一级菜单的id是否相等
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduSubject2, twoSubject);
                    twoSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoSubjectList);
        }
        return oneSubjectList;
    }
}
