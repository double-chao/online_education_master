package com.lcc.excel;

import com.lcc.eduservice.webservice.WebServiceTest;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

/**
 * @Author Administrator
 * @Description 调用webservice接口
 * @Date 2020/9/23  16:58
 */
public class WebServiceClient {

    public static void main(String[] args) {
        main1();
    }

    public static void main1() {
        try {
            // 接口地址
            String address = "http://127.0.0.1:8001/services/webTest?wsdl";
            // 代理工厂
            JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
            // 设置代理地址
            jaxWsProxyFactoryBean.setAddress(address);
            // 设置接口类型
            jaxWsProxyFactoryBean.setServiceClass(WebServiceTest.class);
            // 创建一个代理接口实现
            WebServiceTest webServiceTest = (WebServiceTest) jaxWsProxyFactoryBean.create();
            String info = webServiceTest.getInfo();
            System.out.println("返回结果：" + info);
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 2：动态调用
     */
    public static void main2() {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://127.0.0.1:8001/services/webTest?wsdl");
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME, PASS_WORD));
        try {
            // invoke("方法名",参数1,参数2,参数3....);
            Object[] getInfos = client.invoke("getInfo");
            System.out.println("返回数据:" + getInfos[0]);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}
