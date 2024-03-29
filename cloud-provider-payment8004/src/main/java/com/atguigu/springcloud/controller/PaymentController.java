package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
public class PaymentController {
    @Value("${server.port}")
    private String port;

    @RequestMapping("/payment/zookeeper")
    public String paymentZookeeper() {
        return "Spring Cloud With Zookeeper：" + port + "\t" + UUID.randomUUID();
    }
}
