package com.lcc.eduservice.client;

import com.lcc.eduservice.client.ipml.UserFileDegradeFeignClient;
import com.lcc.vo.UserOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author Administrator
 * @Description 用户信息
 * @Date 2020/9/30  15:31
 */
@FeignClient(name = "service-ucenter", fallback = UserFileDegradeFeignClient.class) //注册中心  需要调用的服务的名字
@Component
public interface UserClient {

    @PostMapping("/educenter/member/getUserInfoOrder/{id}")
    UserOrder getUserInfoOrder(@PathVariable String id);
}
