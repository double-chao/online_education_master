package com.lcc.oaservice.service;

import com.github.pagehelper.PageInfo;
import com.lcc.oaservice.dto.DepartmentQueryDTO;
import com.lcc.oaservice.entity.TDepartment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lcc.oaservice.vo.DepartmentVO;
import com.lcc.pojo.Page;

/**
 * <p>
 * 部门 服务类
 * </p>
 *
 * @author double-chao
 * @since 2021-01-13
 */
public interface DepartmentService extends IService<TDepartment> {

    /**
     * 根据条件查询获取部门分页列表
     *
     * @param departmentQueryDTO 查询条件
     * @param page               分页
     * @return 返回部门列表
     */
    PageInfo<DepartmentVO> listCompanyByCondition(DepartmentQueryDTO departmentQueryDTO, Page page);
}
