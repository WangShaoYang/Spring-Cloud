package com.atguigu.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.alibaba.handler.CustomerBlockHandler;
import com.atguigu.springcloud.alibaba.service.ResourceService;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class FlowLimitController {
    @Resource
    private ResourceService resourceService;

    @GetMapping("/testA")
    public String testA() {
        // 测试线程数时候，开启
        // System.out.println(Thread.currentThread().getName());
        // try {
        //     Thread.sleep(500);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        // 测试链路模式时候，开启
        String resource = resourceService.getResource();
        return "----testA----" + resource;
    }

    @GetMapping("/testB")
    public String testB() {
        // 测试链路模式时候，开启
        String resource = resourceService.getResource();
        return "----testB----" + resource;
    }

    @GetMapping("/testC")
    public String testC() throws InterruptedException {
        // 测试超时降级
        // Thread.sleep(1000);
        // 测试异常降级
        int a = 1 / 0;
        return "----testC----";
    }

    @GetMapping("/testHotKey")
    // value对应资源名，blockHandler表示服务降级自定义方法（注意降级方法和目标方法的返回值要一致）
    @SentinelResource(value = "testHotKey", blockHandler = "dealWith")
    // @RequestParam中，required=false表示当前请求可以不带该参数
    public String testHotKey(@RequestParam(value = "param1", required = false) String param1, @RequestParam(value = "param2", required = false) String param2) {
        return "----testHotKey success----";
    }

    // 参数列表要和目标方法一样，BlockException一定要带上，否则不进这个方法
    public String dealWith(String param1, String param2, BlockException blockException) {
        blockException.printStackTrace();
        return "----testHotKey fail----";
    }

    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public CommonResult byResource() {
        return new CommonResult(200, "按照资源名称限流测试", new Payment(2020L, "serial001"));
    }

    public CommonResult handleException(BlockException exception) {
        return new CommonResult(500, exception.getClass().getCanonicalName() + "\t 服务不可用");
    }

    @GetMapping("/byURL")
    @SentinelResource(value = "byURL")
    public CommonResult byUrl() {
        return new CommonResult(200, "按照byURL限流测试", new Payment(2020L, "serial002"));
    }

    @GetMapping("/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler",
            blockHandlerClass = CustomerBlockHandler.class, blockHandler = "handlerException2")
    public CommonResult customerBlockHandler() {
        return new CommonResult(200, "按照客户自定义限流测试", new Payment(2020L, "serial003"));
    }
}
