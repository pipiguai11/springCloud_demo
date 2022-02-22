package com.lhw.rpc_starter.model;

import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ：linhw
 * @date ：22.2.17 15:45
 * @description：注册服务
 * @modified By：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Service implements Serializable {

    private static final String CUT = "@@";

    /**
     * 本地注册的ip地址
     */
    private String host;

    /**
     * 服务名
     */
    private String serviceName;

    /**
     * 服务暴露端口
     */
    private int port;

    /**
     * 服务接口
     */
    private Class<?> clazz;

    public Instance coverTo() {
        Instance instance = new Instance();
        instance.setIp(host);
        instance.setPort(port);
        instance.setServiceName(serviceName);
        return instance;
    }

    public static Service reCoverTo(Instance instance) {
        return Service.builder()
                .serviceName(extractServiceName(instance.getServiceName()))
                .port(instance.getPort())
                .host(instance.getIp())
                .build();
    }

    private static String extractServiceName(String serviceName) {
        if (null == serviceName) {
            return null;
        }
        return serviceName.split(CUT)[1];
    }

}
