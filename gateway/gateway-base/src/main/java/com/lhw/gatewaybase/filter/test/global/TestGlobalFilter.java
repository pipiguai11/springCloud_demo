package com.lhw.gatewaybase.filter.test.global;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

/**
 * @author ：linhw
 * @date ：21.10.13 15:14
 * @description：测试全局过滤器
 * @modified By：
 */
@Component
@ConditionalOnClass(GatewayAutoConfiguration.class)
public class TestGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("全局过滤器被调用了-------------------------111，我的order值为-1");
        //获取到gateway匹配到的路由配置，不过需要注意的是，这里的路由信息不是这个全局过滤器的信息，而是在配置文件中配置的或者注入到spring容器的局部过滤器的信息
        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
        if (Objects.nonNull(route)){
            System.out.println("当前匹配到的路由是：【" + route.getId() + "】");
            System.out.println("转发的uri是：【" + route.getUri().toString() + "】");
            System.out.println("匹配到的断言是： 【" + route.getPredicate().toString() + "】");
            System.out.println("配置的元数据： 【 " + route.getMetadata() + "】");
            System.out.println("顺序为： 【" + route.getOrder() + "】");
            System.out.println("过滤器设置： + 【" + route.getFilters() + "】");
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        //值越小越先执行
        return -1;
    }
}
