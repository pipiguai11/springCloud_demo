package com.lhw.rpc_starter.annotation;

import java.lang.annotation.*;

/**
 * @author linhw
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcReference {
}
