package com.lhw.rpc_starter.register;

import com.alibaba.nacos.api.exception.NacosException;
import com.lhw.rpc_starter.model.Service;

import java.util.List;

/**
 * @author ：linhw
 * @date ：22.2.21 10:40
 * @description：服务发现
 * @modified By：
 */
public interface Discovery {

    List<Service> findService(String serviceName) throws NacosException;

}
