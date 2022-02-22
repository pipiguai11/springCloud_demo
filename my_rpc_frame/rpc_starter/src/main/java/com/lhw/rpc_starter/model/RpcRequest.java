package com.lhw.rpc_starter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ：linhw
 * @date ：22.2.21 13:23
 * @description：RPC请求对象
 * @modified By：
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest implements Serializable {

    /**
     * 请求ID
     */
    private String requestId;
    /**
     * 需要调用的提供者的类名
     */
    private String className;
    /**
     * 需要调用的方法名
     */
    private String methodName;
    /**
     * 方法的参数类型
     */
    private Class<?>[] parameterTypes;
    /**
     * 方法的具体传参
     */
    private Object[] parameters;

}
