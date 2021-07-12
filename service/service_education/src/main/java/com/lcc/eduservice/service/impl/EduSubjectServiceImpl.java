package com.lcc.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcc.eduservice.entity.EduSubject;
import com.lcc.eduservice.entity.excel.SubjectData;
import com.lcc.eduservice.entity.vo.subject.OneSubjectVO;
import com.lcc.eduservice.entity.vo.subject.TwoSubjectVO;
import com.lcc.eduservice.listener.SubjectExcelListener;
import com.lcc.eduservice.mapper.EduSubjectMapper;
import com.lcc.eduservice.service.EduSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
    public void saveSubjectFile(MultipartFile file, EduSubjectService subjectService) {
        try (InputStream inputStream = file.getInputStream()) {
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubjectVO> getSubjectInfo() {
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id", "0"); //一级菜单的父id一定为0
        List<EduSubject> oneSubject = baseMapper.selectList(oneWrapper); //一级菜单

        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id", "0"); //二级菜单的父id一定不为0
        List<EduSubject> twoSubject = baseMapper.selectList(twoWrapper); //二级菜单

        List<OneSubjectVO> oneSubjectVOList = new ArrayList<>(oneSubject.size());
        //从一级菜单中取基本信息
        for (EduSubject subject1 : oneSubject) {
            OneSubjectVO oneSubjectVO = new OneSubjectVO();
            BeanUtils.copyProperties(subject1, oneSubjectVO); //赋值
            oneSubjectVOList.add(oneSubjectVO); //添加到集合中

            List<TwoSubjectVO> twoSubjectVOList = new ArrayList<>(twoSubject.size());
            //二级菜菜单的信息
            for (EduSubject subject2 : twoSubject) {
                if (subject2.getParentId().equals(subject1.getId())) {  //二级菜单的父id等于一级菜单的id
                    TwoSubjectVO twoSubjectVO = new TwoSubjectVO();
                    BeanUtils.copyProperties(subject2, twoSubjectVO); //赋值
                    twoSubjectVOList.add(twoSubjectVO);
                }
            }
            oneSubjectVO.setTwoSubjectVOList(twoSubjectVOList); //一级菜单添加多个二级菜单
        }
        return oneSubjectVOList;
    }

    @Override
    public boolean saveOneSubject(EduSubject eduSubject) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        String title = eduSubject.getTitle();
        queryWrapper.eq("title", title);
        EduSubject subject = baseMapper.selectOne(queryWrapper);
        if (StringUtils.isEmpty(subject)) {
            int i = baseMapper.insert(eduSubject);
            return i > 0;
        } else {
            return false;
        }
    }

    @Override
    public boolean saveTwoSubjectById(Integer id, Integer sort, String title) {
        EduSubject eduSubject = new EduSubject();
        eduSubject.setParentId(id);
        eduSubject.setSort(sort);
        eduSubject.setTitle(title);
        int i = baseMapper.insert(eduSubject);
        return i > 0;
    }
}
