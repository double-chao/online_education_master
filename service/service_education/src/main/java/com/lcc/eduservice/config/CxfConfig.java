package com.lcc.eduservice.config;

import com.lcc.eduservice.webservice.WebServiceTest;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * @Author Administrator
 * @Description cxf发布webservice配置，在springboot项目启动时，发布webservice接口
 * @Date 2020/9/23  15:59
 */
@Configuration
public class CxfConfig {

    @Autowired
    private Bus bus;
    @Autowired
    WebServiceTest webServiceTest;

    /**
     * 此方法作用是改变项目中服务名的前缀名，此处127.0.0.1或者localhost不能访问时，请使用ipconfig查看本机ip来访问
     * 此方法被注释后:wsdl访问地址为http://127.0.0.1:8080/services/webTest?wsdl
     * 去掉注释后：wsdl访问地址为：http://127.0.0.1:8080/soap/webTest?wsdl
     * @return
     */
//    @SuppressWarnings("all")
//    @Bean
//    public ServletRegistrationBean dispatcherServlet() {
//        return new ServletRegistrationBean(new CXFServlet(), "/soap/*");
//    }


    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus,webServiceTest);
        endpoint.publish("/webTest");
        return endpoint;
    }
}
