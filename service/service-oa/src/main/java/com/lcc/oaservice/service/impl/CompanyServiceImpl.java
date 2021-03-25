package com.lcc.oaservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcc.oaservice.dto.CompanyQueryDTO;
import com.lcc.oaservice.entity.TCompany;
import com.lcc.oaservice.mapper.CompanyMapper;
import com.lcc.oaservice.service.CompanyService;
import com.lcc.oaservice.vo.company.CompanyInfoVO;
import com.lcc.oaservice.vo.company.CompanyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public List<CompanyInfoVO> listCompany() {
        return companyMapper.selectListCompany();
    }

    @Override
    public List<CompanyVO> listCompanyByTree() {
        List<CompanyVO> companyVOList = companyMapper.selectListCompanyByTree();
        return buildCompanyTree(companyVOList);
    }

    /**
     * @param companyVOList 查出来的所有公司列表
     * @return
     */
    private List<CompanyVO> buildCompanyTree(List<CompanyVO> companyVOList) {
        // 创建list集合，用于数据最终封装
        List<CompanyVO> finalNode = new ArrayList<>();
        companyVOList.forEach(companyVO -> { // 遍历所有公司列表
            if (companyVO.getPid() == 0) { // 得到最上级公司
                companyVO.setLevel(1); // 设置顶层公司的level为1
                // 根据顶层菜单，向里面进行查询子菜单，封装到finalNode里面
                finalNode.add(selectChildren(companyVO, companyVOList));
            }
        });
        return finalNode;
    }

    /**
     * @param companyVO     所有顶级菜单
     * @param companyVOList 所有菜单
     * @return
     */
    private CompanyVO selectChildren(CompanyVO companyVO, List<CompanyVO> companyVOList) {
        companyVO.setChildren(new ArrayList<>());
        companyVOList.forEach(companyVO1 -> { // 遍历所有菜单list集合，进行判断比较，比较id和pid值是否相同
            if (companyVO.getId().equals(companyVO1.getPid())) { // 判断 id和pid值是否相同
                int level = companyVO.getLevel() + 1; // 把父菜单的level值+1
                companyVO1.setLevel(level);
                if (companyVO.getChildren() == null) { // 如果children为空，进行初始化操作
                    companyVO.setChildren(new ArrayList<>());
                }
                // 把查询出来的子菜单放到父菜单里面
                companyVO.getChildren().add(selectChildren(companyVO1, companyVOList));
            }
        });
        return companyVO;
    }

    @Override
    public boolean removeAndChildrenById(Integer id) {
        Set<Integer> idSets = new HashSet<>();
        selectCompanyChildById(id, idSets);
        idSets.add(id);
        int i = baseMapper.deleteBatchIds(idSets);
        return i > 0;
    }

    /**
     * 根据当前菜单id，查询菜单里面子菜单id，封装到list集合
     *
     * @param id
     * @param idSet
     */
    private void selectCompanyChildById(Integer id, Set<Integer> idSet) {
        List<TCompany> companyList = companyMapper.selectCompanyById(id);
        companyList.forEach(item -> {
            // 封装idSet里面
            idSet.add(item.getId());
            // 递归查询
            selectCompanyChildById(item.getId(), idSet);
        });
    }

    @Override
    public boolean removeCompanyByIds(Set<Integer> idSets) {
        List<TCompany> companyList = companyMapper.selectSetsCompany(idSets);
        companyList.forEach(item -> idSets.add(item.getId()));
        int i = baseMapper.deleteBatchIds(idSets);
        return i > 0;
    }
}
