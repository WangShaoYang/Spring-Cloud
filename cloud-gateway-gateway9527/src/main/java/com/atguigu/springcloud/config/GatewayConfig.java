package com.atguigu.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                // 当访问http://localhost:9527/payment/get/**的时候，请求会被路由到http://localhost:8001/payment/get/**
                .route("payment_route1", r -> r.path("/payment/get/**").uri("http://localhost:8001/payment/get/**"))
                // 当访问http://localhost:9527/payment/loadbalance的时候，请求会被路由到http://localhost:8001/payment/loadbalance
                .route("payment_route2", r -> r.path("/payment/loadbalance").uri("http://localhost:8001/payment/loadbalance"))
                .build();
    }
}
