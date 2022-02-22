package com.lhw.rpc_starter.proxy;

import com.lhw.rpc_starter.model.RpcRequest;
import com.lhw.rpc_starter.model.RpcResponse;
import com.lhw.rpc_starter.model.Service;
import com.lhw.rpc_starter.register.Discovery;
import com.lhw.rpc_starter.register.NacosDiscovery;
import com.lhw.rpc_starter.remote.Client;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

/**
 * @author ：linhw
 * @date ：22.2.18 17:36
 * @description：调用者代理
 * @modified By：
 */
public class InvokeProxy {

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
            Client client = new Client(service.getHost(), service.getPort());
            client.start();
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
