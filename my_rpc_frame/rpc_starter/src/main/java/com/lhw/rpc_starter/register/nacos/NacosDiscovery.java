package com.lhw.rpc_starter.register.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.lhw.rpc_starter.model.Service;
import com.lhw.rpc_starter.register.Discovery;
import com.lhw.rpc_starter.register.RegisterProperty;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：linhw
 * @date ：22.2.21 10:39
 * @description：nacos服务发现
 * @modified By：
 */
public class NacosDiscovery implements Discovery {

    private NamingService namingService;

    public NacosDiscovery(RegisterProperty property) {
        System.out.println("nacos 发现服务启动");
        try {
            namingService = NamingFactory.createNamingService(property.getAddress());
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Service> findService(String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName)
                .stream()
                .map(Service::reCoverTo)
                .collect(Collectors.toList());
    }
}
