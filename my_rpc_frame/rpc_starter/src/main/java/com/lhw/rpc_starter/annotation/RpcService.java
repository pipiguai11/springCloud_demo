package com.lhw.rpc_starter.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author linhw
 * 这里加上@Component，就可以不用在实现类上加了，先将其注入到上下文中，然后才能正常的注册服务和发现服务
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
//@Component
public @interface RpcService {
}
