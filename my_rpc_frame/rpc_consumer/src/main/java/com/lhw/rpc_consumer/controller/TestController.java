package com.lhw.rpc_consumer.controller;

import com.lhw.rpc_api.test.IService;
import com.lhw.rpc_starter.annotation.RpcReference;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value = "/getMessage", method = RequestMethod.GET)
    public String test(){
        return service.getMessage();
    }

    @RequestMapping(value = "/hello/{msg}", method = RequestMethod.GET)
    public String hello(@PathVariable String msg){
        return service.hello(msg);
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error(){
        return service.testError();
    }

}
