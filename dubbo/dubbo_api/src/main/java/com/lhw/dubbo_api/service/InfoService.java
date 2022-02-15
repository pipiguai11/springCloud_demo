package com.lhw.dubbo_api.service;

import com.lhw.dubbo_api.model.User;

import java.util.List;

public interface InfoService {

    public List<User> getUserInfo();

    public void checkAddr();

    /**
     * 回声测试，检测服务是否可用
     */
    void echoTest();

    void showRpcContextMessage();

}
