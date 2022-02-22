package com.lhw.rpc_starter.register;

import com.lhw.rpc_starter.constant.CommonConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author ：linhw
 * @date ：22.2.17 16:00
 * @description：注册中心配置类
 * @modified By：
 */
@Data
@ConfigurationProperties("rpc.register")
@EnableConfigurationProperties(RegisterProperty.class)
public class RegisterProperty {

    /**
     * 注册中心类型，默认为nacos
     */
    private String type = CommonConstant.Register.DEFAULT_REGISTER;

    /**
     * 注册中心地址
     */
    private String address = CommonConstant.Register.DEFAULT_ADDRESS;

    /**
     * 服务暴露端口
     */
    private int port = CommonConstant.Register.DEFAULT_PORT;

}
