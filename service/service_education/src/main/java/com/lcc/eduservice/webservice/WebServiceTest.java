package com.lcc.eduservice.webservice;

import javax.jws.WebService;
import java.rmi.RemoteException;

/**
 * @Author Administrator
 * @Description 编写webservice接口
 * @Date 2020/9/23
 */
@WebService
public interface WebServiceTest {
    String getInfo() throws RemoteException;
}
