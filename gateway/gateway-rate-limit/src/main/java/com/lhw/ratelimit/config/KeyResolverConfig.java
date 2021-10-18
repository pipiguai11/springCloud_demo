package com.lhw.ratelimit.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author ：linhw
 * @date ：21.10.18 16:45
 * @description：派生密钥配置类
 * @modified By：
 */
@Configuration
public class KeyResolverConfig {

    /**
     * 应用不同的策略来派生限制请求的密钥
     *      这里我是使用的请求的ip来做密钥，也就是做ip限流
     *      我们可以从ServerWebExchange对象中（也就是如下的exchange对象）拿到很多请求相关的信息，包括请求体中的所有内容
     *
     *      比如我还可以做用户限流（通过获取请求头中的userId来做密钥，前提是请求头中携带了）
     *          return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"))
     *      或者接口限流（这里获取到接口的密钥，其实就是请求的uri地址）
     *          return exchange -> Mono.just(exchange.getRequest().getPath().value());}
     * @return
     */
    @Bean
    KeyResolver userKeyResolver() {
        return exchange -> {
            String host = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostString();
            return Mono.just(host);
        };
//        return exchange -> Mono.just("1");
    }

}
