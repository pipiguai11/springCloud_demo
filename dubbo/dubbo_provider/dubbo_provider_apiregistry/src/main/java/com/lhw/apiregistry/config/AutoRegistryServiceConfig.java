package com.lhw.apiregistry.config;

import com.lhw.apiregistry.handler.ApiRegistryHandler;
//import com.lhw.apiregistry.service.AddrServiceImpl;
//import com.lhw.apiregistry.service.UserServiceImpl;
//import com.lhw.apiregistry.service.UserServiceImpl2;
//import com.lhw.dubbo_api.service.AddrService;
//import com.lhw.dubbo_api.service.UserService;
//import org.springframework.context.annotation.Bean;
import com.lhw.apiregistry.service.RpcContextServiceImpl;
import com.lhw.dubbo_api.service.RpcContextService;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author ：linhw
 * @date ：22.2.8 18:36
 * @description：自动注册服务配置
 * @modified By：
 */
@Configuration
public class AutoRegistryServiceConfig {

    private static ApiRegistryHandler apiRegistryHandler = new ApiRegistryHandler();

    /**
     * 在此配置类加载完毕后注册所有服务到注册中心
     */
    @PostConstruct
    public void registryService(){
        //初始化几个服务
//        AddrService addrService = new AddrServiceImpl();
//        UserService userServiceImpl1 = new UserServiceImpl();
//        UserService userServiceImpl2 = new UserServiceImpl2();
        RpcContextService rpcContextService = new RpcContextServiceImpl();

        //暴露服务，注册到注册中心
//        apiRegistryHandler.registryService(AddrService.class,addrService);
//        apiRegistryHandler.registryService(UserService.class,userServiceImpl1);
//        apiRegistryHandler.registryService(UserService.class,userServiceImpl2);
        apiRegistryHandler.registryService(RpcContextService.class, rpcContextService);
    }

}
