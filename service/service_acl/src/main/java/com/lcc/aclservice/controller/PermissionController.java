package com.lcc.aclservice.controller;


import com.lcc.aclservice.entity.Permission;
import com.lcc.aclservice.service.PermissionService;
import com.lcc.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 *
 * @author Admin
 * @since 2020-01-12
 */
@Api(value = "权限/菜单管理")
@RestController
@RequestMapping("/admin/acl/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public Result indexAllPermission() {
        List<Permission> list = permissionService.queryAllMenu();
        return Result.ok().data("children", list);
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Integer id) {
        permissionService.removeChildById(id);
        return Result.ok();
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public Result doAssign(Integer roleId, Integer[] permissionId) {
        permissionService.saveRolePermissionRelationShip(roleId, permissionId);
        return Result.ok();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("/toAssign/{roleId}")
    public Result toAssign(@PathVariable Integer roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return Result.ok().data("children", list);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping("/save")
    public Result save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("/update")
    public Result updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.ok();
    }

}

