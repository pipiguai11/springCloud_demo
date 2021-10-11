//package com.lhw.gateway.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author ：linhw
// * @date ：21.10.8 17:33
// * @description：网关配置
// * @modified By：
// */
//@Configuration
//public class GatewayConfig {
//
//    @Bean
//    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(p -> p
//                        .path("/json/parse/array")
//                        .uri("http://localhost:30010"))
//                .build();
//    }
//
//}
