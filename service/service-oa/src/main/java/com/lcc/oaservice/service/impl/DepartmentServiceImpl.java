package com.lcc.oaservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcc.oaservice.dto.DepartmentQueryDTO;
import com.lcc.oaservice.entity.TCompany;
import com.lcc.oaservice.entity.TDepartment;
import com.lcc.oaservice.mapper.DepartmentMapper;
import com.lcc.oaservice.service.CompanyService;
import com.lcc.oaservice.service.DepartmentService;
import com.lcc.oaservice.vo.DepartmentVO;
import com.lcc.pojo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 部门 服务实现类
 * </p>
 *
 * @author double-chao
 * @since 2021-01-13
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, TDepartment> implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private CompanyService companyService;

    @Override
    public PageInfo<DepartmentVO> listCompanyByCondition(DepartmentQueryDTO departmentQueryDTO, Page page) {
        PageHelper.startPage(page.getPage(), page.getSize());
        List<DepartmentVO> departmentVOList = departmentMapper.selectListDepartmentByCondition(departmentQueryDTO);
        departmentVOList.forEach(departmentVO -> {
            Integer companyId = departmentVO.getCompanyId();
            TCompany company = companyService.getById(companyId);
            String name = company.getName();
            departmentVO.setCompanyName(name);
        });
        return new PageInfo<>(departmentVOList);
    }
}
