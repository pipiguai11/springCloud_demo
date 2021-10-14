package com.lhw.gatewaybase.filter.test.local;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：linhw
 * @date ：21.10.13 16:12
 * @description：测试局部过滤器
 *
 *      其实就是自定义一个配置类，然后注入到容器中即可
 *      path：等同于配置文件中的Path
 *      filters：等同于配置文件中的filters
 *      metadata：配置元数据，等同于配置文件中的metadata
 *      uri：等同配置文件中的uri
 *
 *      需要注意的是顺序问题，这里必须是最后的时候设置uri，因为uri方法返回的和前面几个方法不一样
 *
 *      测试访问时：直接访问http://localhost:8901/testLocalFilter/get/myname
 *
 * @modified By：
 */
@Configuration
public class TestLocalFilter {

    @Bean
//    @ConditionalOnBean(RouteLocatorBuilder.class)
    public RouteLocator myRoute(RouteLocatorBuilder builder){
        return builder.routes().route("testLocalFilter_1", p -> p
                        .path("/testLocalFilter/get/myname")
                        .filters(f -> f.rewritePath("/testLocalFilter/?(?<segment>.*)","/$\\{segment}"))
                        .metadata("name","lhw")
                        .uri("lb://nacos-demo"))
                .build();
    }
}
