package com.lcc.eduorder.client.impl;

import com.lcc.eduorder.client.UserClient;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.vo.UserOrder;
import org.springframework.stereotype.Component;

/**
 * @Author: 一十六
 * @Description: 服务熔断后，执行的方法
 * @Date: 2020/5/30 17:37
 */
@Component
public class UserInfoDegradeFeignClient implements UserClient {

    @Override
    public UserOrder getUserInfoOrder(String id) {

        throw new BadException(20001,"获取用户信息失败");
    }
}
