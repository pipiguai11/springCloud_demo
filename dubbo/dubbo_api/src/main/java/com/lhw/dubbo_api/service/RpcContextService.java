package com.lhw.dubbo_api.service;

/**
 * @author ：linhw
 * @date ：22.2.14 14:08
 * @description：用于测试服务提供者内部调用另外的服务提供者的服务时的RpcContext变化
 * @modified By：
 */
public interface RpcContextService {

    void invoke();

}
