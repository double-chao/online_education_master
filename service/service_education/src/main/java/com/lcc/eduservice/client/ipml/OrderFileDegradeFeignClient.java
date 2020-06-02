package com.lcc.eduservice.client.ipml;

import com.lcc.eduservice.client.OrderClient;
import com.lcc.servicebase.exceptionhandler.BadException;
import org.springframework.stereotype.Component;

/**
 * @Author: 一十六
 * @Description: 服务熔断后，执行的方法
 * @Date: 2020/5/30 17:37
 */
@Component
public class OrderFileDegradeFeignClient implements OrderClient {

    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        throw new BadException(20001,"查询课程是否购买失败");
    }
}
