package com.lcc.security.util;

import com.lcc.servicebase.exceptionhandler.BadException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Author Administrator
 * @Description 获取当前登录的用户
 * @Date 2020/11/13  17:56
 */
public class SecurityUtils {

    public static UserDetails getUserDetails() {
        UserDetails userDetails;
        try {
            userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new BadException(HttpStatus.UNAUTHORIZED, "登录状态过期");
        }
        return userDetails;
    }

    /**
     * 获取系统用户名称
     * @return 系统用户名称
     */
//    public static String getUsername(){
//        Object obj = getUserDetails();
//        return new JSONObject(obj).get("username", String.class);
//    }
}
