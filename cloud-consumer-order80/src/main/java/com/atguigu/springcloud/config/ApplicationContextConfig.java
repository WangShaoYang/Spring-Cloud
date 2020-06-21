package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    @Bean
    // 使用@LoadBalanced注解赋予RestTemplate负载均衡的能力
    // 这就是Ribbon的负载均衡功能
    // @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
