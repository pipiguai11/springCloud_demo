package com.lhw.gatewaybase.filter;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.lhw.gatewaybase.factory.AbstractRequestFactory;
import com.lhw.gatewaybase.factory.WebFluxRequestFactory;
import com.lhw.gatewaybase.module.CustomCachedBodyOutputMessage;
import com.lhw.gatewaybase.module.Request;
import com.lhw.gatewaybase.module.RequestContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author ：linhw
 * @date ：21.10.12 13:09
 * @description：自定义全局过滤器实现
 * @modified By：
 */
@Component
@ConditionalOnClass(GatewayAutoConfiguration.class)
public class MyGatewayGlobalFilter extends AbstractGatewayFilter implements GlobalFilter, Ordered {
    private final Logger logger = LoggerFactory.getLogger(MyGatewayGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerRequest serverRequest = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());
        //获取参数类型
        String contentType = exchange.getRequest().getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
        // 解析参数
        AbstractRequestFactory requestFactory = new WebFluxRequestFactory();
        Request authRequest = requestFactory.createRequest(exchange.getRequest());
        RequestContainer.setTemp(authRequest);
        Map<String, String> requestParamsMap = new HashMap<>(16);
        Mono<String> modifiedBody = serverRequest.bodyToMono(String.class)
                .publishOn(Schedulers.immediate())
                .flatMap(originalBody -> {
                    // 根据请求头，用不同的方式解析Body
                    if (StringUtils.isNotEmpty(contentType)) {
                        if (contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
                            this.parseRequestBody(requestParamsMap, originalBody);
                        } else if (contentType.startsWith(MediaType.APPLICATION_JSON_VALUE)) {
                            this.parseRequestJson(requestParamsMap, originalBody);
                        } else if (contentType.startsWith(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
                            this.parseRequestQuery(requestParamsMap, originalBody);
                        }
                    }
                    // 加载QueryParameter
                    this.parseRequestQuery(requestParamsMap, exchange.getRequest().getQueryParams());
                    logger.info("所有参数：{}", JSON.toJSONString(requestParamsMap));
                    // 把信息放置到线程容器内
                    authRequest.setParameters(requestParamsMap);
                    RequestContainer.set(authRequest);
                    return Mono.just(originalBody);
                });
        logger.info("所有参数：{}", JSON.toJSONString(requestParamsMap));
        // 把修改过的参数、消费过的参数，重新封装发布
        BodyInserter<Mono<String>, ReactiveHttpOutputMessage> bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getRequest().getHeaders());
        headers.remove(HttpHeaders.CONTENT_LENGTH);
        CustomCachedBodyOutputMessage outputMessage = new CustomCachedBodyOutputMessage(
                exchange, headers);
        Mono<Void> result = bodyInserter.insert(outputMessage, new BodyInserterContext())
                .then(Mono.defer(() -> {
                    ServerHttpRequest decorator = decorate(exchange, headers,
                            outputMessage);
                    return chain
                            .filter(exchange.mutate().request(decorator).build());
                })).onErrorResume(
                        (Function<Throwable, Mono<Void>>) throwable -> release(
                                exchange, outputMessage, throwable));
        return result;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1000;
    }
}