package com.lhw.apiregistry.handler;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;

/**
 * @author ：linhw
 * @date ：22.2.8 18:30
 * @description：
 * @modified By：
 */
public class ApiRegistryHandler {

    private static ApplicationConfig applicationConfig;

    private static RegistryConfig registryConfig;

    static {
        //配置应用信息
        applicationConfig = new ApplicationConfig();
        applicationConfig.setName("consumer");
        //配置注册中心
        registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
    }

    public <T> void registryService(Class<T> interfaceName, T refObject){
        ServiceConfig<T> serviceConfig = new ServiceConfig<>();
        serviceConfig.setApplication(applicationConfig);
        serviceConfig.setRegistry(registryConfig);

        serviceConfig.setInterface(interfaceName);
        serviceConfig.setRef(refObject);

        //暴露并注册服务
        serviceConfig.export();
    }

}
