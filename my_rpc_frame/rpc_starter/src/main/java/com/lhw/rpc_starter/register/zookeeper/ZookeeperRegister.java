package com.lhw.rpc_starter.register.zookeeper;

import com.alibaba.fastjson.JSON;
import com.lhw.rpc_starter.constant.CommonConstant;
import com.lhw.rpc_starter.model.Service;
import com.lhw.rpc_starter.register.Register;
import com.lhw.rpc_starter.register.RegisterProperty;
import com.lhw.rpc_starter.util.ZookeeperUtil;
import org.apache.zookeeper.ZooKeeper;


/**
 * @author ：linhw
 * @date ：22.2.17 17:18
 * @description：zookeeper注册中心
 * @modified By：
 */
public class ZookeeperRegister implements Register {

    private ZooKeeper zooKeeper;

    public ZookeeperRegister(RegisterProperty registerProperty){
        System.out.println("zookeeper注册服务启动");
        zooKeeper = ZookeeperUtil.connect(registerProperty.getAddress(), 3000);
    }

    @Override
    public void register(Service service) {
        String path = CommonConstant.Register.ZOOKEEPER_NODE_PREFIX + "/"
                + service.getServiceName() + "/" + CommonConstant.Register.PROVIDER;
        ZookeeperUtil.queryStat(zooKeeper,path);
        ZookeeperUtil.cascadeCreateNode(zooKeeper, path, JSON.toJSONString(service));
    }
}
