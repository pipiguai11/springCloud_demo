package com.lhw.rpc_starter.proxy;

import com.lhw.rpc_starter.model.RpcRequest;
import com.lhw.rpc_starter.model.RpcResponse;
import com.lhw.rpc_starter.model.Service;
import com.lhw.rpc_starter.register.Discovery;
import com.lhw.rpc_starter.remote.Client;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author ：linhw
 * @date ：22.2.18 17:36
 * @description：调用者代理
 * @modified By：
 */
@Slf4j
public class InvokeProxy {

    private static Map<String, Client> CLIENT_MAP = new ConcurrentHashMap<>(16);
    private Discovery discovery;

    public InvokeProxy(Discovery discovery) {
        this.discovery = discovery;
    }

    public Object getProxy(Class<?> clazz) {
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvokeProxyHandler(clazz));
    }

    private class InvokeProxyHandler implements InvocationHandler {

        private Class<?> clazz;

        public InvokeProxyHandler(Class<?> clazz) {
            this.clazz = clazz;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //1.拿到服务名，构建远程RPCRequest
            String serviceName = clazz.getName();
            RpcRequest request = RpcRequest.builder()
                    .requestId(UUID.randomUUID().toString())
                    .className(serviceName)
                    .methodName(method.getName())
                    .parameterTypes(method.getParameterTypes())
                    .parameters(args)
                    .build();
            //2.从注册中心中获取服务列表，然后挑选一个进行远程调用
            List<Service> services = discovery.findService(serviceName);
            Service service = services.get((new Random().nextInt() % services.size()));
            //3.实例化客户端连接，发起调用请求
            Client client = null;
            /**
             * 将客户端连接保存本地，后面针对同一个IP_PORT的服务器就只建立一个连接
             * 然后所有的RPC请求都通过这个连接进行通信，防止每次发起一次RPC请求就建立一次连接
             *
             * 这里有一点需要注意，因为指向同一台服务器的所有RPC请求都用的同一个连接，因此它的通道数据需要重置
             * 否则本次请求获取的结果会是上一次请求的结果，具体的可以看到 {@see com.lhw.rpc_starter.remote.ClientHandler} 的getRpcResponse方法
             */
            String key = service.getHost() + "_" + service.getPort();
            if (!CLIENT_MAP.containsKey(key)){
                client = new Client(service.getHost(), service.getPort());
                client.start();
                //需要确保客户端正常连上了服务端才能正常通信，否则会有很多问题
                TimeUnit.SECONDS.sleep(2);
                CLIENT_MAP.put(key, client);
            }else {
                client = CLIENT_MAP.get(key);
            }

            RpcResponse response = client.invoke(request);
            //4.拿到响应结果RPCResponse并返回
            if (!Objects.isNull(response.getResult())) {
                return response.getResult();
            } else {
                return response.getError();
            }

        }
    }

}
