package com.lcc.oaservice.mapper;

import com.lcc.oaservice.dto.DepartmentQueryDTO;
import com.lcc.oaservice.entity.TDepartment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lcc.oaservice.vo.DepartmentVO;

import java.util.List;

/**
 * <p>
 * 部门 Mapper 接口
 * </p>
 *
 * @author double-chao
 * @since 2021-01-13
 */
public interface DepartmentMapper extends BaseMapper<TDepartment> {

    /**
     * 分页查询公司列表
     *
     * @param departmentQueryDTO 查询参数
     * @return 返回列表
     */
    List<DepartmentVO> selectListDepartmentByCondition(DepartmentQueryDTO departmentQueryDTO);
}
