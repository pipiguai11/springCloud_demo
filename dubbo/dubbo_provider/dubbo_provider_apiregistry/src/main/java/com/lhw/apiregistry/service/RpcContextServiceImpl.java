package com.lhw.apiregistry.service;

import com.lhw.dubbo_api.service.RpcContextService;
import com.lhw.dubbo_api.utils.RpcContextUtil;
import org.apache.dubbo.rpc.RpcContext;

/**
 * @author ：linhw
 * @date ：22.2.14 14:09
 * @description：用于测试服务提供者内部调用另外的服务提供者的服务时的RpcContext变化
 * @modified By：
 */
public class RpcContextServiceImpl implements RpcContextService {
    @Override
    public void invoke() {
        System.out.println();
        System.out.println();
        System.out.println("这是另外的服务提供者的服务（RpcContextServiceImpl），调用成功");
//        System.out.println("|---- consumerUrl-application : " + rpcContext.getConsumerUrl().getApplication());
//        System.out.println("|---- remoteHost : " + rpcContext.getRemoteHost());
        RpcContextUtil.showRpcContextMessage();
        System.out.println("从RpcContext中取出来的隐式参数 index： " + RpcContext.getServiceContext().getAttachment("index"));
        System.out.println("从RpcContext中取出来的隐式参数 name： " + RpcContext.getServiceContext().getAttachment("name"));
        System.out.println();
        System.out.println();
    }
}
