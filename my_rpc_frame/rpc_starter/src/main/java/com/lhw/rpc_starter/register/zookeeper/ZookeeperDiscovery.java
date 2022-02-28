package com.lhw.rpc_starter.register.zookeeper;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.exception.NacosException;
import com.lhw.rpc_starter.constant.CommonConstant;
import com.lhw.rpc_starter.model.Service;
import com.lhw.rpc_starter.register.Discovery;
import com.lhw.rpc_starter.register.RegisterProperty;
import com.lhw.rpc_starter.util.ZookeeperUtil;
import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：linhw
 * @date ：22.2.28 13:59
 * @description：zookeeper发现服务
 * @modified By：
 */
public class ZookeeperDiscovery implements Discovery {

    private ZooKeeper zooKeeper;

    public ZookeeperDiscovery(RegisterProperty property){
        System.out.println("zookeeper 发现服务启动");
        zooKeeper = ZookeeperUtil.connect(property.getAddress(),5000);
    }

    @Override
    public List<Service> findService(String serviceName) throws NacosException {
        String path = CommonConstant.Register.ZOOKEEPER_NODE_PREFIX + "/"
                + serviceName + "/" + CommonConstant.Register.PROVIDER;
        List<Service> services = new ArrayList<>();
        services.add(JSON.parseObject(ZookeeperUtil.queryData(zooKeeper, path), Service.class));
        return services;
    }
}
