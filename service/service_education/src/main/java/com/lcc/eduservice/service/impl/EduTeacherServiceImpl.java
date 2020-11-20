package com.lcc.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcc.eduservice.entity.EduTeacher;
import com.lcc.eduservice.entity.vo.ObjectPageInfo;
import com.lcc.eduservice.entity.vo.TeacherQuery;
import com.lcc.eduservice.mapper.EduTeacherMapper;
import com.lcc.eduservice.service.EduTeacherService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public ObjectPageInfo selectAllTeacherPageInfo(long current, long size, TeacherQuery teacherQuery) {
        // 分页  当前页，多少条
        Page<EduTeacher> pageTeacher = new Page<>(current, size);
        // 构建查询对象
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        // 查询语句条件拼接  相当于动态sql
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name); //双引号中的值为数据库语句中的字段名字
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        wrapper.orderByDesc("gmt_create"); //创建时间排序
        this.page(pageTeacher, wrapper);
        long total = pageTeacher.getTotal(); //总记录条数
        List<EduTeacher> teacherList = pageTeacher.getRecords(); //集合对象
        ObjectPageInfo objectPage = new ObjectPageInfo();
        objectPage.setTotal(total);
        objectPage.setList(teacherList);
        return objectPage;
    }

    @Cacheable(value = {"teacherFront"},key = "#root.method.name")
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
