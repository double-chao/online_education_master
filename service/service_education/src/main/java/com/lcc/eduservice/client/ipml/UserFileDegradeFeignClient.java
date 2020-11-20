package com.lcc.eduservice.client.ipml;

import com.lcc.eduservice.client.UserClient;
import com.lcc.vo.UserOrder;
import org.springframework.stereotype.Component;

/**
 * @Author: 一十六
 * @Description: 服务熔断后，执行的方法
 * @Date: 2020/5/30 17:37
 */
@Component
public class UserFileDegradeFeignClient implements UserClient {
    @Override
    public UserOrder getUserInfoOrder(String id) {
        return null;
    }

}
