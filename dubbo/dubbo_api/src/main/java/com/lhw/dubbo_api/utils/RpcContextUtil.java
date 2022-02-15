package com.lhw.dubbo_api.utils;

import org.apache.dubbo.rpc.RpcContext;

/**
 * @author ：linhw
 * @date ：22.2.14 14:20
 * @description：RpcContext工具类
 * @modified By：
 */
public class RpcContextUtil {

    public static void showRpcContextMessage(){
        // 本端是否为消费端/提供端
        // 当被远程调用了，那就是提供端，当调用了远程服务，那就是消费端
        // 每个应用都可以既是提供端又是消费端的
        System.out.println();
        System.out.println("是否为消费端 ： " + RpcContext.getServiceContext().isConsumerSide());
        System.out.println("是否为提供端 ： " + RpcContext.getServiceContext().isProviderSide());
        // 获取最后一次调用的提供方IP地址
        System.out.println("最后一次调用的提供方IP地址 : " + RpcContext.getServiceContext().getRemoteHost());
        // 获取当前服务配置信息，所有配置信息都将转换为URL的参数
        System.out.println("当前服务配置信息 : " + RpcContext.getServiceContext().getUrl().getParameter("application"));
        System.out.println();
    }

}
