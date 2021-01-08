package com.lcc.msmservice.service;

public interface MsmService {
    /**
     * 发送短信的方法
     * @param phone
     * @return
     */
    boolean sendMsm(String phone);
}
