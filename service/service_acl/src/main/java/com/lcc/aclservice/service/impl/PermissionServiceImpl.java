package com.lcc.aclservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcc.aclservice.entity.Permission;
import com.lcc.aclservice.entity.RolePermission;
import com.lcc.aclservice.entity.User;
import com.lcc.aclservice.helper.MenuHelper;
import com.lcc.aclservice.helper.PermissionHelper;
import com.lcc.aclservice.mapper.PermissionMapper;
import com.lcc.aclservice.service.PermissionService;
import com.lcc.aclservice.service.RolePermissionService;
import com.lcc.aclservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private UserService userService;

    //根据角色获取菜单
    @Override
    public List<Permission> selectAllMenu(Integer roleId) {
        List<Permission> allPermissionList = baseMapper.selectList(
                new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));

        //根据角色id获取角色权限
        List<RolePermission> rolePermissionList = rolePermissionService.list(
                new QueryWrapper<RolePermission>().eq("role_id", roleId));
        //转换给角色id与角色权限对应Map对象
//        List<String> permissionIdList = rolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
//        allPermissionList.forEach(permission -> {
//            if(permissionIdList.contains(permission.getId())) {
//                permission.setSelect(true);
//            } else {
//                permission.setSelect(false);
//            }
//        });
        for (Permission permission : allPermissionList) {
            for (RolePermission rolePermission : rolePermissionList) {
                if (rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }
        return build(allPermissionList);
    }

    //根据用户id获取用户菜单
    @Override
    public List<String> selectPermissionValueByUserId(Integer id) {

        List<String> selectPermissionValueList;
        if (this.isSysAdmin(id)) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(Integer userId) {
        List<Permission> selectPermissionList;
        if (this.isSysAdmin(userId)) {
            //如果是超级管理员，获取所有菜单
            selectPermissionList = this.baseMapper.selectList(null);
        } else {
            selectPermissionList = this.baseMapper.selectPermissionByUserId(userId);
            selectPermissionList.add(this.getById(1));
        }
        List<Permission> permissionList = PermissionHelper.build(selectPermissionList);
        return MenuHelper.build(permissionList);
    }

    /**
     * 判断用户是否系统管理员
     *
     * @param userId 用户id
     * @return 返回值
     */
    private boolean isSysAdmin(Integer userId) {
        User user = userService.getById(userId);
        return null != user && "admin".equals(user.getUsername());
    }

    /**
     * 递归获取子节点
     *
     * @param id    父id
     * @param idSet id集合
     */
    private void selectChildListById(Integer id, Set<Integer> idSet) {
        List<Permission> childList = baseMapper.selectList(new QueryWrapper<Permission>().eq("pid", id).select("id"));
        childList.forEach(item -> {
            idSet.add(item.getId());
            this.selectChildListById(item.getId(), idSet);
        });
    }

    /**
     * 使用递归方法建菜单
     *
     * @param treeNodes 权限集合
     * @return 返回的权限list集合
     */
    private static List<Permission> build(List<Permission> treeNodes) {
        List<Permission> trees = new ArrayList<>();
        for (Permission treeNode : treeNodes) {
            if (treeNode.getPid() == 0) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNode  权限
     * @param treeNodes 权限集合
     * @return 返回的权限
     */
    private static Permission findChildren(Permission treeNode, List<Permission> treeNodes) {
        treeNode.setChildren(new ArrayList<>());
        for (Permission it : treeNodes) {
            if (treeNode.getId().equals(it.getPid())) {
                int level = treeNode.getLevel() + 1;
                it.setLevel(level);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }

    /**
     * 获取全部菜单
     *
     * @return 返回值：权限集合
     */
    @Override
    public List<Permission> queryAllMenu() {
        //1 查询菜单表所有数据
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissionList = baseMapper.selectList(wrapper);
        //2 把查询所有菜单list集合按照要求进行封装
        return buildPermission(permissionList);
    }

    //把返回所有菜单list集合进行封装的方法
    public static List<Permission> buildPermission(List<Permission> permissionList) {
        //创建list集合，用于数据最终封装
        List<Permission> finalNode = new ArrayList<>();
        //把所有菜单list集合遍历，得到顶层菜单 pid=0菜单，设置level是1
        for (Permission permissionNode : permissionList) {
            //得到顶层菜单 pid=0菜单
            if (permissionNode.getPid() == 0) {
                //设置顶层菜单的level是1
                permissionNode.setLevel(1);
                //根据顶层菜单，向里面进行查询子菜单，封装到finalNode里面
                finalNode.add(selectChildren(permissionNode, permissionList));
            }
        }
        return finalNode;
    }

    private static Permission selectChildren(Permission permissionNode, List<Permission> permissionList) {
        //1 因为向一层菜单里面放二层菜单，二层里面还要放三层，把对象初始化
        permissionNode.setChildren(new ArrayList<>());

        //2 遍历所有菜单list集合，进行判断比较，比较id和pid值是否相同
        for (Permission it : permissionList) {
            //判断 id和pid值是否相同
            if (permissionNode.getId().equals(it.getPid())) {
                //把父菜单的level值+1
                int level = permissionNode.getLevel() + 1;
                it.setLevel(level);
                //如果children为空，进行初始化操作
                if (permissionNode.getChildren() == null) {
                    permissionNode.setChildren(new ArrayList<>());
                }
                //把查询出来的子菜单放到父菜单里面
                permissionNode.getChildren().add(selectChildren(it, permissionList));
            }
        }
        return permissionNode;
    }

    /**
     * 递归删除菜单
     *
     * @param id 权限id
     */
    @Override
    public void removeChildById(Integer id) {
        //1 创建list集合，用于封装所有删除菜单id值
//        List<String> idList = new ArrayList<>();
        Set<Integer> idSet = new HashSet<>();
        //2 向idList集合设置删除菜单id
        this.selectPermissionChildById(id, idSet);
        //把当前id封装到list里面
        idSet.add(id);
        baseMapper.deleteBatchIds(idSet);
    }

    //2 根据当前菜单id，查询菜单里面子菜单id，封装到list集合
    private void selectPermissionChildById(Integer id, Set<Integer> idSet) {
        //查询菜单里面子菜单id
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", id);
        wrapper.select("id");
        List<Permission> childIdList = baseMapper.selectList(wrapper);
        //把childIdList里面菜单id值获取出来，封装idList里面，做递归查询
        childIdList.forEach(item -> {
            //封装idList里面
            idSet.add(item.getId());
            //递归查询
            this.selectPermissionChildById(item.getId(), idSet);
        });
    }

    /**
     * 给角色分配菜单
     *
     * @param roleId        角色id
     * @param permissionIds 权限id数组
     */
    @Override
    public void saveRolePermissionRelationShip(Integer roleId, Integer[] permissionIds) {
        //roleId角色id
        //permissionId菜单id 数组形式
        //1 创建list集合，用于封装添加数据
        List<RolePermission> rolePermissionList = new ArrayList<>();
        //遍历所有菜单数组
        for (Integer perId : permissionIds) {
            //RolePermission对象
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(perId);
            //封装到list集合
            rolePermissionList.add(rolePermission);
        }
        //添加到角色菜单关系表
        rolePermissionService.saveBatch(rolePermissionList);
    }
}
