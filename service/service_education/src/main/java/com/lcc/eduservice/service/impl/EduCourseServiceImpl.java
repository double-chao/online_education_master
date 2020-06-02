package com.lcc.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcc.eduservice.entity.EduCourse;
import com.lcc.eduservice.entity.EduCourseDescription;
import com.lcc.eduservice.entity.frontvo.CourseFrontVo;
import com.lcc.eduservice.entity.frontvo.CourseWebVo;
import com.lcc.eduservice.entity.vo.CourseInfoVo;
import com.lcc.eduservice.entity.vo.CoursePublishVo;
import com.lcc.eduservice.entity.vo.CourseQuery;
import com.lcc.eduservice.entity.vo.ObjectPageInfo;
import com.lcc.eduservice.mapper.EduCourseMapper;
import com.lcc.eduservice.service.EduChapterService;
import com.lcc.eduservice.service.EduCourseDescriptionService;
import com.lcc.eduservice.service.EduCourseService;
import com.lcc.eduservice.service.EduVideoService;
import com.lcc.servicebase.exceptionhandler.BadException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程基本信息 服务实现类
 * </p>
 *
 * @author chaochao
 * @since 2020-05-28
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private EduChapterService chapterService;

    @Override
    public ObjectPageInfo selectAllCoursePageInfo(long current, long size, CourseQuery courseQuery) {
        Page<EduCourse> coursePage = new Page<>(current, size);
        // 构建查询对象
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        // 查询语句条件拼接  相当于动态sql
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("gmt_create"); //创建时间排序
        page(coursePage, wrapper);
        long total = coursePage.getTotal(); //总记录条数
        List<EduCourse> records = coursePage.getRecords(); //集合对象
        ObjectPageInfo objectPageInfo = new ObjectPageInfo();
        objectPageInfo.setTotal(total);
        objectPageInfo.setList(records);
        return objectPageInfo;
    }

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, course);
        int i = baseMapper.insert(course);
        if (i > 0) { //还要添加课程描述
            String courseId = course.getId(); //得到添加后的课程id
            EduCourseDescription courseDescription = new EduCourseDescription();
            courseDescription.setId(courseId); //课程描述的id和课程id为一样的
            courseDescription.setDescription(courseInfoVo.getDescription());
            courseDescriptionService.save(courseDescription);
            return courseId;
        } else {
            throw new BadException(20001, "添加课程信息失败");
        }
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0) {
            throw new BadException(20001, "修改课程信息失败");
        }
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);
    }

    @Override
    public CoursePublishVo getPublishVoInfo(String courseId) {
        return baseMapper.selectPublishVoInfo(courseId);
    }

    @Override
    public void removeCourse(String courseId) {
        eduVideoService.removeVideoByCourseId(courseId);
        chapterService.removeChapterByCourseId(courseId);
        courseDescriptionService.removeById(courseId);
        int result = baseMapper.deleteById(courseId); //逻辑删除
        if (result == 0) {
            throw new BadException(20001, "删除失败！");
        }
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            wrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(coursePage, wrapper);
        List<EduCourse> courseList = coursePage.getRecords();
        long current = coursePage.getCurrent(); //当前页
        long pages = coursePage.getPages(); //共有多少页
        long size = coursePage.getSize();  //一页多少条
        long total = coursePage.getTotal(); //一共多少条
        boolean hasNext = coursePage.hasNext(); //上一页
        boolean hasPrevious = coursePage.hasPrevious(); //下一页
        Map<String, Object> map = new HashMap<>();
        map.put("items", courseList);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.selectWebCourseInfo(courseId);
    }
}
