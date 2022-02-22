package com.lhw.rpc_starter.register;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.lhw.rpc_starter.model.Service;

/**
 * @author ：linhw
 * @date ：22.2.17 15:54
 * @description：nacos服务注册器
 * @modified By：
 */
public class NacosRegister implements Register {

    private NamingService namingService;

    public NacosRegister(RegisterProperty registerProperty) {
        try {
            namingService = NamingFactory.createNamingService(registerProperty.getAddress());
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(Service service) {
        Instance instance = service.coverTo();
        try {
            namingService.registerInstance(service.getServiceName(), instance);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }
}
