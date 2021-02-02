package com.lcc.oaservice.mapper;

import com.lcc.oaservice.dto.CompanyQueryDTO;
import com.lcc.oaservice.entity.TCompany;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lcc.oaservice.vo.CompanyVO;

import java.util.List;

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
}
