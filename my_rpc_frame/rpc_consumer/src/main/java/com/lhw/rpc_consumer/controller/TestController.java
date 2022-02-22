package com.lhw.rpc_consumer.controller;

import com.lhw.rpc_api.test.IService;
import com.lhw.rpc_starter.annotation.RpcReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：linhw
 * @date ：22.2.21 17:47
 * @description：测试
 * @modified By：
 */
@RestController
public class TestController {

    @RpcReference
    private IService service;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
        return service.getMessage();
    }
}
