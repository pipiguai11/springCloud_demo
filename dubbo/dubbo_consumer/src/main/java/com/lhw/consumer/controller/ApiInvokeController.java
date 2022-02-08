package com.lhw.consumer.controller;

import com.lhw.consumer.service.ApiInvokeService;
import com.lhw.dubbo_api.service.AddrService;
import com.lhw.dubbo_api.service.InfoService;
import com.lhw.dubbo_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：linhw
 * @date ：22.2.8 17:36
 * @description：测试通过API的方式发现服务并调用远程服务的方法
 * @modified By：
 */
@RestController
public class ApiInvokeController {

    @Autowired
    ApiInvokeService apiInvokeService;

    /**
     *  测试用例：http://localhost:8889/api/UserService/userInfo
     *
     * @param interfaceName  提供者的服务接口
     * @param method  调用的目标方法（无参的）
     * @return
     */
    @RequestMapping("api/{interfaceName}/{method}")
    public String api(@PathVariable String interfaceName, @PathVariable String method){
        Class<?> clazz = null;
        switch (interfaceName){
            case "AddrService" : clazz = AddrService.class; break;
            case "InfoService" : clazz = InfoService.class; break;
            case "UserService" : clazz = UserService.class; break;
            default: clazz = Object.class;
        }
        return apiInvokeService.invokeService(clazz,method).toString();
    }

}
