package com.lcc.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcc.eduservice.entity.EduTeacher;
import com.lcc.eduservice.mapper.EduTeacherMapper;
import com.lcc.eduservice.service.EduTeacherService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author chaochao
 * @since 2020-05-25
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        baseMapper.selectPage(pageTeacher, wrapper);
        List<EduTeacher> records = pageTeacher.getRecords(); //讲师对象集合
        long current = pageTeacher.getCurrent(); //当前页
        long pages = pageTeacher.getPages(); //共有多少页
        long size = pageTeacher.getSize();  //一页多少条
        long total = pageTeacher.getTotal(); //一共多少条
        boolean hasNext = pageTeacher.hasNext(); //上一页
        boolean hasPrevious = pageTeacher.hasPrevious(); //下一页
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }
}
