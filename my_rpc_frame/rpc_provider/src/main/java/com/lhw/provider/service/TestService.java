package com.lhw.provider.service;

import com.lhw.rpc_api.test.IService;
import com.lhw.rpc_starter.annotation.RpcService;
import org.springframework.stereotype.Component;

/**
 * @author ：linhw
 * @date ：22.2.21 16:50
 * @description：测试服务
 * @modified By：
 */
@RpcService
@Component
public class TestService implements IService {

    @Override
    public String hello(String msg) {
        return "hello world" + msg;
    }

    @Override
    public String getMessage() {
        return "Success";
    }

    @Override
    public String testError() {
        try {
            int i = 1 / 0;
        }catch (Exception e){
            return e.getMessage();
        }
        return "error";
    }


}
