package com.lcc.msmservice.service;

import java.util.Map;

public interface MsmService {
    /**
     * 发送短信的方法
     * @param param
     * @param phone
     * @return
     */
    boolean send(Map<String, Object> param, String phone);
}
