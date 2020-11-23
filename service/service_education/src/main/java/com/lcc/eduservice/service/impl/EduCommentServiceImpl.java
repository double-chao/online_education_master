package com.lcc.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcc.eduservice.entity.EduComment;
import com.lcc.eduservice.entity.EduCourse;
import com.lcc.eduservice.entity.vo.CommentQuery;
import com.lcc.eduservice.entity.vo.CommentVO;
import com.lcc.eduservice.entity.vo.ObjectPageInfo;
import com.lcc.eduservice.mapper.EduCommentMapper;
import com.lcc.eduservice.service.EduCommentService;
import com.lcc.eduservice.service.EduCourseService;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.servicebase.exceptionhandler.CodeEnum;
import com.lcc.util.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author chaochao
 * @since 2020-06-06
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Autowired
    private EduCourseService courseService;

    @Override
    public ObjectPageInfo selectCommentPageInfo(long current, long size, CommentQuery commentQuery) {
        Page<EduComment> commentPage = new Page<>(current, size);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        String title = commentQuery.getTitle();
        String begin = commentQuery.getBegin();
        String end = commentQuery.getEnd();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("course_title", title);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        wrapper.orderByDesc("gmt_create");
        this.page(commentPage, wrapper);
        long total = commentPage.getTotal();
        List<EduComment> commentList = commentPage.getRecords();

        List<CommentVO> commentVOList = new ArrayList<>(commentList.size()); //返回给管理系统的数据
        for (EduComment comment : commentList) {
            String courseId = comment.getCourseId();
            EduCourse eduCourse = courseService.getById(courseId);
            CommentVO commentVO = new CommentVO();
            commentVO.setTitle(eduCourse.getTitle());
            BeanUtils.copyProperties(comment, commentVO);
            commentVOList.add(commentVO);
        }
        ObjectPageInfo pageInfo = new ObjectPageInfo();
        pageInfo.setTotal(total);
        pageInfo.setList(commentVOList);
        return pageInfo;
    }

    @Override
    public boolean saveComment(EduComment comment, HttpServletRequest request) {
        boolean checkToken = JwtUtils.checkToken(request);
        if (checkToken) {
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            if (StringUtils.isEmpty(memberId)) {
                throw new BadException(CodeEnum.USER_NO_LOGIN_EXCEPTION);
            }
            // 在课程详情页面,传过来课程id,可以根据课程id把讲师信息查出来，然后在进行赋值
            String courseId = comment.getCourseId();
            if (!StringUtils.isEmpty(courseId)) {
                QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
                wrapper.eq("id", courseId);
                EduCourse course = courseService.getOne(wrapper);
                comment.setTeacherId(course.getTeacherId());
                comment.setCourseTitle(course.getTitle());
                comment.setMemberId(memberId);
                int i = this.baseMapper.insert(comment);
                return i > 0;
            } else {
                throw new BadException(CodeEnum.COURSE_INFO_NOT_EXITS);
            }
        } else {
            throw new BadException(CodeEnum.LOGIN_EXPIRED_EXCEPTION);
        }
    }

    @Override
    public Map<String, Object> listCourseFrontById(String courseId, Page<EduComment> commentPage) {
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseId)) {
            wrapper.eq("course_id", courseId);
        }
        wrapper.orderByDesc("id");
        baseMapper.selectPage(commentPage, wrapper);
        List<EduComment> records = commentPage.getRecords(); //课程评论对象集合
        long current = commentPage.getCurrent(); //当前页
        long pages = commentPage.getPages(); //共有多少页
        long size = commentPage.getSize();  //一页多少条
        long total = commentPage.getTotal(); //一共多少条
        boolean hasNext = commentPage.hasNext(); //上一页
        boolean hasPrevious = commentPage.hasPrevious(); //下一页
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
