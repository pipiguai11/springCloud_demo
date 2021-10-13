package com.lhw.gatewaybase.module;

import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author ：linhw
 * @date ：21.10.12 13:31
 * @description：包装CacheBodyOutputMessage类
 * @modified By：
 */
public class CustomCachedBodyOutputMessage extends CachedBodyOutputMessage {

    public boolean isCached() {
        return true;
    }

    public CustomCachedBodyOutputMessage(ServerWebExchange exchange, HttpHeaders httpHeaders) {
        super(exchange, httpHeaders);
    }
}
