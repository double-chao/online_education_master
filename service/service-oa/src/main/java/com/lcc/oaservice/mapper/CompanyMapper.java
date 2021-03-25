package com.lcc.oaservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lcc.oaservice.dto.CompanyQueryDTO;
import com.lcc.oaservice.entity.TCompany;
import com.lcc.oaservice.vo.company.CompanyInfoVO;
import com.lcc.oaservice.vo.company.CompanyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 公司 Mapper 接口
 * </p>
 *
 * @author double-chao
 * @since 2021-01-13
 */
public interface CompanyMapper extends BaseMapper<TCompany> {

    /**
     * 分页查询公司列表
     *
     * @param companyQueryDTO 查询参数
     * @return 返回列表
     */
    List<CompanyVO> selectListCompanyByCondition(CompanyQueryDTO companyQueryDTO);

    /**
     * 获取公司列表-id,名称,父公司id
     *
     * @return
     */
    List<CompanyInfoVO> selectListCompany();

    /**
     * 获取公司列表-树形结构展示
     *
     * @return
     */
    List<CompanyVO> selectListCompanyByTree();

    /**
     * 根据公司id 查询公司该公司下的子公司
     *
     * @param id
     * @return
     */
    List<TCompany> selectCompanyById(@Param("id") Integer id);

    /**
     * 遍历公司id, 查询公司及其该公司下的子公司
     *
     * @param idSets
     * @return
     */
    List<TCompany> selectSetsCompany(@Param("idSets") Set<Integer> idSets);
}
