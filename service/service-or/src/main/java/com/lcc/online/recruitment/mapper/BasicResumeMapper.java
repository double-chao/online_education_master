package com.lcc.online.recruitment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lcc.online.recruitment.dto.ResumeQueryDTO;
import com.lcc.online.recruitment.entity.BasicResume;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 基础简历表 Mapper 接口
 * </p>
 *
 * @author double-chao
 * @since 2021-05-23
 */
public interface BasicResumeMapper extends BaseMapper<BasicResume> {

    /**
     * 根据查询条件，获取简历数据集合
     *
     * @param resumeQueryDTO 条件查询对象
     * @return 简历数据集合
     */
    List<BasicResume> listBaseResumeByCondition(@Param("resumeQueryDTO") ResumeQueryDTO resumeQueryDTO);

    /**
     * 根据简历id，获取基础简历信息
     *
     * @param id 简历id
     * @return 简历信息
     */
    BasicResume selectBasicResumeById(@Param("id") Integer id);
}
