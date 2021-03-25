package com.lcc.oaservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.lcc.oaservice.dto.CompanyQueryDTO;
import com.lcc.oaservice.entity.TCompany;
import com.lcc.oaservice.vo.company.CompanyInfoVO;
import com.lcc.oaservice.vo.company.CompanyVO;

import java.util.List;
import java.util.Set;

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

    /**
     * 获取公司列表-id,名称,父公司id
     *
     * @return
     */
    List<CompanyInfoVO> listCompany();

    /**
     * 获取公司列表-树形结构展示
     *
     * @return
     */
    List<CompanyVO> listCompanyByTree();

    /**
     * 根据公司id-递归删除公司
     *
     * @param id 公司id
     * @return
     */
    boolean removeAndChildrenById(Integer id);

    /**
     * 批量递归删除公司及其子公司
     *
     * @param idSets
     * @return
     */
    boolean removeCompanyByIds(Set<Integer> idSets);
}
