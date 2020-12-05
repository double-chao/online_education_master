package com.lcc.eduservice.client.ipml;

import com.lcc.eduservice.client.UserClient;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.servicebase.exceptionhandler.CodeEnum;
import com.lcc.vo.UserOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: 一十六
 * @Description: 服务熔断后，执行的方法
 * @Date: 2020/5/30 17:37
 */
@Slf4j
@Component
public class UserFileDegradeFeignClient implements UserClient {
    @Override
    public UserOrder getUserInfoOrder(Integer id) {
        log.error("远程调用用户服务，执行熔断器，获取用户信息失败");
        throw new BadException(CodeEnum.GET_USER_INFO_FAILED_EXCEPTION);
    }

}
