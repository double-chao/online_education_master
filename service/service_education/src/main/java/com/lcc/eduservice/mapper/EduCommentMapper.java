package com.lcc.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lcc.eduservice.entity.EduComment;
import com.lcc.eduservice.entity.vo.CommentQuery;
import com.lcc.eduservice.entity.vo.CommentVO;

import java.util.List;

/**
 * <p>
 * 评论 Mapper 接口
 * </p>
 *
 * @author chaochao
 * @since 2020-06-06
 */
public interface EduCommentMapper extends BaseMapper<EduComment> {

    /**
     * 根据查询条件获取相应的评论信息
     * @param commentQuery
     * @return
     */
    List<CommentVO> selectCommentVOByCondition(CommentQuery commentQuery);
}
