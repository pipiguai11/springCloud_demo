package com.lhw.rpc_starter.config;

import com.lhw.rpc_starter.condition.EnableZookeeperRegister;
import com.lhw.rpc_starter.listener.RpcApplicationListener;
import com.lhw.rpc_starter.proxy.InvokeProxy;
import com.lhw.rpc_starter.register.*;
import com.lhw.rpc_starter.register.nacos.NacosDiscovery;
import com.lhw.rpc_starter.register.nacos.NacosRegister;
import com.lhw.rpc_starter.register.zookeeper.ZookeeperDiscovery;
import com.lhw.rpc_starter.register.zookeeper.ZookeeperRegister;
import com.lhw.rpc_starter.remote.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：linhw
 * @date ：22.2.17 16:55
 * @description：自动加载Bean
 * @modified By：
 */
@Configuration
public class AutoConfig {

    @Bean
    public RegisterProperty registerProperty() {
        return new RegisterProperty();
    }

    @Bean
    public Server server(@Autowired RegisterProperty property){
        return new Server(property.getPort());
    }

    //    注册中心选择开始
    @Bean("registry")
    @Conditional(EnableZookeeperRegister.class)
    public Register zookeeperRegister(@Autowired RegisterProperty property) {
        return new ZookeeperRegister(property);
    }

    @Bean("discovery")
    @Conditional(EnableZookeeperRegister.class)
    public Discovery zookeeperDiscovery(@Autowired RegisterProperty property){
        return new ZookeeperDiscovery(property);
    }

    @Bean("registry")
    @ConditionalOnMissingBean(Register.class)
    public Register nacosRegister(RegisterProperty registerProperty) {
        return new NacosRegister(registerProperty);
    }

    @Bean("discovery")
    @ConditionalOnMissingBean
    public Discovery nacosDiscovery(RegisterProperty registerProperty) {
        return new NacosDiscovery(registerProperty);
    }

    //    注册中心选择结束

    @Bean
    public InvokeProxy invokeProxy(@Qualifier("discovery") @Autowired Discovery discovery) {
        return new InvokeProxy(discovery);
    }

    @Bean
    public RpcApplicationListener rpcApplicationListener(RegisterProperty registerProperty,
                                                         @Autowired @Qualifier("registry") Register register,
                                                         InvokeProxy invokeProxy,
                                                         Server server) {
        return new RpcApplicationListener(registerProperty, register, invokeProxy, server);
    }


}
