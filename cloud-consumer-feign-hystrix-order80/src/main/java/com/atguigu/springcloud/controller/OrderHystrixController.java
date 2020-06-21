package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
// 指定全局降级方法，对带了@HystrixCommand注解的方法起作用
@DefaultProperties(defaultFallback = "paymentGlobalFallbackMethod")
public class OrderHystrixController {
    @Resource
    PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        return paymentHystrixService.paymentInfo_OK(id);
    }

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    // @HystrixCommand注解表示，当目标方法不满足commandProperties指定的参数时，终止当前方法，继而执行fallbackMethod指定的方法
    // @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMehtod", commandProperties = {
    //         @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3500")
    // })
    // 带了@HystrixCommand注解表示，走Hystrix的服务降级策略，而且此时没有指明自定义的callback，就走默认的callback
    @HystrixCommand(commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2500")})
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) throws InterruptedException {
        // int a = 1 / 0; // 当程序报错，也会触发服务降级方法
        return paymentHystrixService.paymentInfo_TimeOut(id);
    }

    // 服务降级的方法
    // 需要注意的是，服务降级方法要和目标方法的方法签名保持一致，即参数和返回值要一致，否则会提示找不到服务降级方法
    public String paymentTimeOutFallbackMehtod(@PathVariable("id") Integer id) {
        return "消费者调用生产者繁忙，请稍等后再试";
    }

    // 全局服务降级方法，@HystrixCommand中，如果没有指定fallbackMethod的，就走全局服务降级方法
    public String paymentGlobalFallbackMethod() {
        return "全局服务降级方法，请稍后再试";
    }
}
