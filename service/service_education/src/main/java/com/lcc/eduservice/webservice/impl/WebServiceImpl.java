package com.lcc.eduservice.webservice.impl;

import com.lcc.eduservice.webservice.WebServiceTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.rmi.RemoteException;

/**
 * @Author Administrator
 * @Description 测试服务接口实现类
 * @Date 2020/9/23  14:51
 */
@WebService(serviceName="WebServiceTest",//对外发布的服务名
        targetNamespace="http://webservice.eduservice.lcc.com",//指定你想要的名称空间，通常使用使用包名反转
        endpointInterface="com.lcc.eduservice.webservice.WebServiceTest")//服务接口全路径, 指定做SEI（Service EndPoint Interface）服务端点接口
@Component
@Slf4j
public class WebServiceImpl implements WebServiceTest {

    @Override
    public String getInfo() throws RemoteException {
        log.debug("获取信息,调用了webservice接口.....log");
        System.out.println("获取信息,调用了webservice接口.....sout");
        return "调用了webservice接口";
    }

}
