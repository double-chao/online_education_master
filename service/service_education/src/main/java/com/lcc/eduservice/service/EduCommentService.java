package com.lcc.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lcc.eduservice.entity.EduComment;
import com.lcc.eduservice.entity.vo.CommentQuery;
import com.lcc.eduservice.entity.vo.ObjectPageInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author chaochao
 * @since 2020-06-06
 */
public interface EduCommentService extends IService<EduComment> {

    /**
     * 分页查询课程评论信息
     * @param current
     * @param size
     * @param commentQuery
     * @return
     */
    ObjectPageInfo selectCommentPageInfo(long current, long size, CommentQuery commentQuery);

    /**
     * 添加评论
     * @param comment
     * @param request
     * @return
     */
    boolean saveComment(EduComment comment, HttpServletRequest request);

    /**
     *  前台-分页查询课程评论-根据课程id
     * @param courseId
     * @param commentPage
     * @return
     */
    Map<String, Object> listCourseFrontById(Integer courseId ,Page<EduComment> commentPage);

}
