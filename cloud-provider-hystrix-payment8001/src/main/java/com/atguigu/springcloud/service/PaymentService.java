package com.atguigu.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Service
public class PaymentService {
    public String paymentInfo_OK(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "\tpaymentInfo_OK, id=" + id;
    }

    // @HystrixCommand注解表示，当目标方法不满足commandProperties指定的参数时，终止当前方法，继而执行fallbackMethod指定的方法
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfo_TimeOut(Integer id) throws InterruptedException {
        int time = 5000;
        // int a = 1 / 0;// 当程序报错，也会触发服务降级方法
        Thread.sleep(time);
        return "线程池：" + Thread.currentThread().getName() + "\tpaymentInfo_TimeOut, id=" + id + "\t耗时：" + time;
    }

    // 服务降级的方法
    // 需要注意的是，服务降级方法要和目标方法的方法签名保持一致，即参数和返回值要一致，否则会提示找不到服务降级方法
    public String paymentInfo_TimeOutHandler(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "\tpaymentInfo_TimeOutHandler, id=" + id + "\t运行了服务降级方法";
    }

    // 服务熔断，这里配置的HystrixProperty可以在https://github.com/Netflix/Hystrix/wiki/Configuration查看，也可以查看HystrixCommandProperties类
    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerFallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),// 是否开启断路器
            // 一个rolling window内请求的次数，当请求次数达到10，才计算失败率，从而判断是否满足断路的条件
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            // 触发断路后的10s都会直接失败，超过10s后，尝试一次恢复
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            // 失败率达到60%的时候，进行断路
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("id不能为负数");
        }
        String uuid = UUID.randomUUID().toString();
        return Thread.currentThread().getName() + "\t调用成功，UUID=" + uuid;
    }

    // 服务降级方法
    public String paymentCircuitBreakerFallback(@PathVariable("id") Integer id) {
        return "id不能为负数，请稍后再试，id=" + id;
    }
}
