package com.lcc.eduservice.client;

import com.lcc.eduservice.client.ipml.OrderFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: 一十六
 * @Description: 调用注册中心中订单提供的服务
 * @Date: 2020/5/30 14:51
 */
@FeignClient(name = "service-order", fallback = OrderFileDegradeFeignClient.class) //注册中心  需要调用的服务的名字
@Component
public interface OrderClient {

    /**
     * 方法的路径  是提供服务方的完整路径，建议把路径和方法名一起复制过来
     * ！！！注意：@PathVariable  注解中一定要QWz指定参数名称，否则会出错
     *
     * @param courseId
     * @param memberId
     * @return
     */
    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{memberId}")
    boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);

}
