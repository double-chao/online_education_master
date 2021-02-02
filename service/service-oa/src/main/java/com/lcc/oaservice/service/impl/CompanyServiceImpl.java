package com.lcc.oaservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcc.oaservice.dto.CompanyQueryDTO;
import com.lcc.oaservice.entity.TCompany;
import com.lcc.oaservice.mapper.CompanyMapper;
import com.lcc.oaservice.service.CompanyService;
import com.lcc.oaservice.vo.CompanyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 公司 服务实现类
 * </p>
 *
 * @author double-chao
 * @since 2021-01-13
 */
@Slf4j
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, TCompany> implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public PageInfo<CompanyVO> listCompanyByCondition(CompanyQueryDTO companyQueryDTO, Integer current, Integer size) {
        PageHelper.startPage(current, size);
        List<CompanyVO> companyVOList = companyMapper.selectListCompanyByCondition(companyQueryDTO);
        return new PageInfo<>(companyVOList);
    }
}
