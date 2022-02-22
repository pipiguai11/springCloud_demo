package com.lhw.rpc_starter.condition;

import com.lhw.rpc_starter.constant.CommonConstant;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author ：linhw
 * @date ：22.2.17 17:32
 * @description：zookeeper注册中心判断器
 * @modified By：
 */
public class EnableZookeeperRegister implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return CommonConstant.Register.ZOOKEEPER_TYPE.equals(context.getEnvironment().getProperty("rpc.register.type"));
    }
}
