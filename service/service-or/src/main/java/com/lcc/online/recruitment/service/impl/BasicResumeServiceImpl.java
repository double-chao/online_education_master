package com.lcc.online.recruitment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcc.online.recruitment.constant.*;
import com.lcc.online.recruitment.dto.BasicResumeDTO;
import com.lcc.online.recruitment.dto.ResumeQueryDTO;
import com.lcc.online.recruitment.entity.BasicResume;
import com.lcc.online.recruitment.mapper.BasicResumeMapper;
import com.lcc.online.recruitment.service.BasicResumeService;
import com.lcc.online.recruitment.util.ResumeCodeUtil;
import com.lcc.online.recruitment.vo.BasicResumeVO;
import com.lcc.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 基础简历表 服务实现类
 * </p>
 *
 * @author double-chao
 * @since 2021-05-23
 */
@Slf4j
@RestController
public class BasicResumeServiceImpl extends ServiceImpl<BasicResumeMapper, BasicResume> implements BasicResumeService {

    @Override
    public Result list(ResumeQueryDTO resumeQueryDTO) {
        transformQuery(resumeQueryDTO);
        PageHelper.startPage(resumeQueryDTO.getPage(), resumeQueryDTO.getSize());
        List<BasicResume> resumeList = baseMapper.listBaseResumeByCondition(resumeQueryDTO);
        if (!CollectionUtils.isEmpty(resumeList)) {
            List<BasicResumeVO> resumeVOList = new ArrayList<>(resumeList.size());
            resumeList.forEach(basicResume -> {
                BasicResumeVO basicResumeVO = new BasicResumeVO();
                BeanUtils.copyProperties(basicResume, basicResumeVO);
                transformSelect(basicResume, basicResumeVO);
                resumeVOList.add(basicResumeVO);
            });
            PageInfo<BasicResumeVO> pageInfo = new PageInfo<>(resumeVOList);
            long total = pageInfo.getTotal();
            List<BasicResumeVO> resumeVOS = pageInfo.getList();
            return Result.ok().data("total", total).data("resumeVOS", resumeVOS);
        }
        return Result.ok();
    }

    @Override
    public Result getById(Integer id) {
        BasicResume basicResume = baseMapper.selectBasicResumeById(id);
        BasicResumeVO basicResumeVO = new BasicResumeVO();
        BeanUtils.copyProperties(basicResume, basicResumeVO);
        transformSelect(basicResume, basicResumeVO);
        return Result.ok().data("basicResumeVO", basicResumeVO);
    }

    @Override
    public Result save(BasicResumeDTO basicResumeDTO) {
        BasicResume basicResume = new BasicResume();
        transformSaveOrUpdate(basicResumeDTO, basicResume, true);
        String idCard = basicResumeDTO.getIdCard();
        ResumeQueryDTO resumeQueryDTO = new ResumeQueryDTO();
        resumeQueryDTO.setIdCard(idCard);
        List<BasicResume> resumeList = baseMapper.listBaseResumeByCondition(resumeQueryDTO);
        if (!CollectionUtils.isEmpty(resumeList)) {
            return Result.fail().message("身份证号已存在");
        }
        BeanUtils.copyProperties(basicResumeDTO, basicResume);
        basicResume.setResumeCode(ResumeCodeUtil.getResumeCode());
        int i = baseMapper.insert(basicResume);
        return i == 1 ? Result.ok() : Result.fail();
    }

    @Override
    public Result update(BasicResumeDTO basicResumeDTO) {
        BasicResume basicResume = new BasicResume();
        BeanUtils.copyProperties(basicResumeDTO, basicResume);
        transformSaveOrUpdate(basicResumeDTO, basicResume, false);
        int i = baseMapper.updateById(basicResume);
        return i == 1 ? Result.ok() : Result.fail();
    }

    @Override
    public Result deletedById(Integer id) {
        int i = baseMapper.deleteById(id);
        return i == 1 ? Result.ok() : Result.fail();
    }

    @Override
    public Result deletedByBatch(Set<Integer> idSet) {
        if (!CollectionUtils.isEmpty(idSet)) {
            int size = idSet.size();
            int number = baseMapper.deleteBatchIds(idSet);
            if (size == number) {
                return Result.ok();
            }
            if (size > number && number > 0) {
                return Result.fail().message("删除简历部分成功");
            }
            if (number == 0) {
                return Result.fail();
            }
        }
        return Result.ok();
    }

    /**
     * 渲染回显：数据库值转换成页面显示的值
     *
     * @param basicResume   数据库中简历对象
     * @param basicResumeVO 页面回显对象
     */
    private void transformSelect(BasicResume basicResume, BasicResumeVO basicResumeVO) {
        String sexName = SexEnum.getNameByCode(basicResume.getSex());
        basicResumeVO.setSex(sexName);
        String applyJobName = ApplyJobEnum.getNameByCode(basicResume.getApplyJob());
        basicResumeVO.setApplyJob(applyJobName);
        String applyLevelName = ApplyLevelEnum.getNameByCode(basicResume.getApplyLevel());
        basicResumeVO.setApplyLevel(applyLevelName);
        String resumeFromName = ResumeFromEnum.getNameByCode(basicResume.getResumeFrom());
        basicResumeVO.setResumeFrom(resumeFromName);
    }

    /**
     * 添加或者更新时：页面值转换成数据库中的值
     *
     * @param basicResumeDTO 页面传输对象
     * @param basicResume    数据库中需存对象
     * @param flag           true: 添加操作，false: 更新操作
     */
    private void transformSaveOrUpdate(BasicResumeDTO basicResumeDTO, BasicResume basicResume, boolean flag) {
        Byte applyJobCode = ApplyJobEnum.getCodeByName(basicResumeDTO.getApplyJob()); // 岗位
        basicResume.setApplyJob(applyJobCode);
        Byte applyLevelCode = ApplyLevelEnum.getCodeByName(basicResumeDTO.getApplyLevel()); // 应聘职级
        basicResume.setApplyLevel(applyLevelCode);

        String sex = basicResumeDTO.getSex();
        if (!StringUtils.isEmpty(sex)) { // 性别不为空
            Byte sexCode = SexEnum.getCodeByName(sex);
            if (ObjectUtils.isEmpty(sexCode)) {
                basicResume.setSex(NumberConstant.TWO);
            } else {
                basicResume.setSex(sexCode);
            }
        } else { // 性别为空
            if (flag) { // 添加操作
                basicResume.setSex(NumberConstant.TWO);
            }
        }

        String resumeFrom = basicResumeDTO.getResumeFrom();
        if (!StringUtils.isEmpty(resumeFrom)) { // 简历来源不为空
            Byte resumeFromCode = ResumeFromEnum.getCodeByName(resumeFrom);
            if (ObjectUtils.isEmpty(resumeFromCode)) {
                basicResume.setResumeFrom(NumberConstant.ONE);
            } else {
                basicResume.setResumeFrom(resumeFromCode);
            }
        } else { // 简历来源为空
            if (flag) { // 添加操作
                basicResume.setResumeFrom(NumberConstant.ONE);
            }
        }
    }

    /**
     * 查询时：前端传过来的对象值转换为数据库中想要查询的值
     *
     * @param resumeQueryDTO 查询对象
     */
    private void transformQuery(ResumeQueryDTO resumeQueryDTO) {
        if (!ObjectUtils.isEmpty(resumeQueryDTO)) {
            String applyJob = resumeQueryDTO.getApplyJob();
            if (!StringUtils.isEmpty(applyJob)) {
                Byte applyJobCode = ApplyJobEnum.getCodeByName(applyJob);
                if (!ObjectUtils.isEmpty(applyJobCode)) {
                    resumeQueryDTO.setApplyJob(applyJobCode.toString());
                }
            }
            String applyLevel = resumeQueryDTO.getApplyLevel();
            if (!StringUtils.isEmpty(applyLevel)) {
                Byte applyLevelCode = ApplyLevelEnum.getCodeByName(applyLevel);
                if (!ObjectUtils.isEmpty(applyLevelCode)) {
                    resumeQueryDTO.setApplyLevel(applyLevelCode.toString());
                }
            }
            String resumeFrom = resumeQueryDTO.getResumeFrom();
            if (!StringUtils.isEmpty(resumeFrom)) {
                Byte resumeFromCode = ResumeFromEnum.getCodeByName(resumeFrom);
                if (!ObjectUtils.isEmpty(resumeFromCode)) {
                    resumeQueryDTO.setResumeFrom(resumeFromCode.toString());
                }
            }
        }
    }
}
