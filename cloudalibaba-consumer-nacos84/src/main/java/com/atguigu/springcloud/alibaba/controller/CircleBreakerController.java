package com.atguigu.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.alibaba.service.PaymentService;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class CircleBreakerController {
    // nacos-payment-provider就是9003和9004微服务的spring.application.name的值
    public static final String SERVICE_URL = "http://nacos-payment-provider";
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private PaymentService paymentService;

    @RequestMapping("/consumer/fallback/{id}")
    // @SentinelResource(value = "fallback") // 无配置
    // @SentinelResource(value = "fallback", fallback = "handlerFallback") // 只配置fallback
    // @SentinelResource(value = "fallback", blockHandler = "blockHandler") // 只配置blockHandler
    // @SentinelResource(value = "fallback", fallback = "handlerFallback", blockHandler = "blockHandler") // fallback和blockHandler都配置
    @SentinelResource(value = "fallback", fallback = "handlerFallback", blockHandler = "blockHandler", exceptionsToIgnore = {NullPointerException.class}) // 添加exceptionsToIgnore属性
    public CommonResult<Payment> fallback(@PathVariable Long id) {
        CommonResult<Payment> result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, CommonResult.class, id);
        if (id == 4) {
            throw new IllegalArgumentException("IllegalArgument，非法参数异常...");
        } else if (result.getData() == null) {
            throw new NullPointerException("NullPointerException，该ID没有对应记录，空指针异常");
        }
        return result;
    }

    public CommonResult handlerFallback(@PathVariable Long id, Throwable throwable) {
        Payment payment = new Payment(id, null);
        return new CommonResult(444, "异常fallback方法，异常内容是：" + throwable.getMessage(), payment);
    }

    public CommonResult blockHandler(@PathVariable Long id, BlockException e) {
        Payment payment = new Payment(id, "null");
        return new CommonResult(444, "blockHandler-sentinel限流，BlockException：" + e.getMessage(), payment);
    }

    @GetMapping("/consumer/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id) {
        return paymentService.paymentSQL(id);
    }
}
