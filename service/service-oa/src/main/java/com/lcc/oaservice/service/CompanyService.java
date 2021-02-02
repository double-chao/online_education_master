package com.lcc.oaservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.lcc.oaservice.dto.CompanyQueryDTO;
import com.lcc.oaservice.entity.TCompany;
import com.lcc.oaservice.vo.CompanyVO;

/**
 * <p>
 * 公司 服务类
 * </p>
 *
 * @author double-chao
 * @since 2021-01-13
 */
public interface CompanyService extends IService<TCompany> {

    /**
     * 根据条件查询获取公司分页列表
     *
     * @param companyQueryDTO 查询条件
     * @param current         当前页
     * @param size            多少条
     * @return 返回公司列表
     */
    PageInfo<CompanyVO> listCompanyByCondition(CompanyQueryDTO companyQueryDTO, Integer current, Integer size);
}
