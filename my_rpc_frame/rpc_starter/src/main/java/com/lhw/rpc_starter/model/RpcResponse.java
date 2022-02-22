package com.lhw.rpc_starter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ：linhw
 * @date ：22.2.21 13:23
 * @description：RPC响应对象
 * @modified By：
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcResponse implements Serializable {

    /**
     * 请求ID
     */
    private String requestId;
    /**
     * 错误信息
     */
    private String error;
    /**
     * 返回结果
     */
    private Object result;

}
