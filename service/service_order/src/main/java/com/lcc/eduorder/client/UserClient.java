package com.lcc.eduorder.client;

import com.lcc.eduorder.client.impl.UserInfoDegradeFeignClient;
import com.lcc.vo.UserOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author: 一十六
 * @Description: 远程调用 用户提供的接口  得到用户信息
 * @Date: 2020/6/2 16:32
 */
@FeignClient(name = "service-ucenter", fallback = UserInfoDegradeFeignClient.class)
@Component
public interface UserClient {

    /**
     * 根据用户id获取订单用户信息
     * @param id
     * @return
     */
    @PostMapping("/educenter/member/getUserInfoOrder/{id}")
    UserOrder getUserInfoOrder(@PathVariable("id") Integer id);

}
