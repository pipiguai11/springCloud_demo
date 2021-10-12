package com.lhw.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.StringUtils;
import com.lhw.gateway.module.CustomCachedBodyOutputMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ：linhw
 * @date ：21.10.12 13:09
 * @description：抽象gateway过滤器
 * @modified By：
 */
public class AbstractGatewayFilter implements GlobalFilter, Ordered {

    private final Logger logger = LoggerFactory.getLogger(AbstractGatewayFilter.class);

    protected final static String parameterReg = "-{28}([0-9]{24})\r\n.+name=\"(\\S*)\"\r\n\r\n(\\S*)";
    protected final static String fileParameterReg = "-{28}([0-9]{24})\r\n.+name=\"(\\S*)\"; filename=\"(\\S*)\"\r\n.*\r\n\r\n";


    protected void parseRequestBody(Map<String, String> parameterMap, String parameterString) {
        this.regexParseBodyString(parameterReg, parameterMap, parameterString);
        this.regexParseBodyString(fileParameterReg, parameterMap, parameterString);
    }

    protected void parseRequestJson(Map<String, String> parameterMap, String parameterString) {
        final JSONObject object = JSON.parseObject(parameterString);
        for (String key : object.keySet()) {
            parameterMap.put(key, object.getString(key));
        }
    }

    protected void parseRequestQuery(Map<String, String> parameterMap, MultiValueMap<String, String> queryParamMap) {
        if (queryParamMap != null && !queryParamMap.isEmpty()) {
            for (String key : queryParamMap.keySet()) {
                final List<String> stringList = queryParamMap.get(key);
                parameterMap.put(key, stringList != null && !stringList.isEmpty() ? StringUtils.join(Arrays.asList(stringList.toArray()), ",") : null);
            }
        }
    }

    protected void parseRequestQuery(Map<String, String> parameterMap, String parameterString) {
        final String[] paramsStr = parameterString.split("&");
        for (String s : paramsStr) {
            logger.info("请求名：" + s.split("=")[0]);
            logger.info("请求值：" + s.split("=")[1]);
            parameterMap.put(s.split("=")[0], s.split("=")[1]);
        }
    }

    protected void regexParseBodyString(String reg, Map<String, String> parameterMap, String bodyStr) {
        Matcher matcher = Pattern.compile(reg).matcher(bodyStr);
        while (matcher.find()) {
            parameterMap.put(matcher.group(2), matcher.group(3));
            logger.info("请求参数编号：" + matcher.group(1));
            logger.info("请求名：" + matcher.group(2));
            logger.info("请求值：" + matcher.group(3));
        }
    }


    protected Mono<Void> release(ServerWebExchange exchange,
                                 CustomCachedBodyOutputMessage outputMessage, Throwable throwable) {
        if (outputMessage.isCached()) {
            return outputMessage.getBody().map(DataBufferUtils::release)
                    .then(Mono.error(throwable));
        }
        return Mono.error(throwable);
    }

    protected ServerHttpRequestDecorator decorate(ServerWebExchange exchange, HttpHeaders headers,
                                                  CachedBodyOutputMessage outputMessage) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                if (contentLength > 0) {
                    httpHeaders.setContentLength(contentLength);
                } else {
                    httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                }
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return outputMessage.getBody();
            }
        };
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
