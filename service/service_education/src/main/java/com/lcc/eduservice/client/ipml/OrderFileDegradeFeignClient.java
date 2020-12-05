package com.lcc.eduservice.client.ipml;

import com.lcc.eduservice.client.OrderClient;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.servicebase.exceptionhandler.CodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: 一十六
 * @Description: 服务熔断后，执行的方法
 * @Date: 2020/5/30 17:37
 */
@Slf4j
@Component
public class OrderFileDegradeFeignClient implements OrderClient {

    @Override
    public boolean isBuyCourse(Integer courseId, Integer memberId) {
        log.error("远程调用订单服务，执行熔断器，查询课程是否购买失败");
        throw new BadException(CodeEnum.COURSE_IS_BUY_FAILED);
    }
}
