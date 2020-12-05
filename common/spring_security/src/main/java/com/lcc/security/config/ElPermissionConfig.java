package com.lcc.security.config;

import com.lcc.servicebase.exceptionhandler.BadException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Administrator
 * @Description 权限配置
 * @Date 2020/11/13  17:56
 */
@Service(value = "el")
public class ElPermissionConfig {

    public Boolean check(String... permissions) {
        // 获取当前用户的所有权限
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BadException(HttpStatus.UNAUTHORIZED, "当前登录状态过期");
        }
        List<String> elPermissions = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        // 判断当前用户的所有权限是否包含接口上定义的权限
        return elPermissions.contains("admin") || Arrays.stream(permissions).anyMatch(elPermissions::contains);
    }
}
