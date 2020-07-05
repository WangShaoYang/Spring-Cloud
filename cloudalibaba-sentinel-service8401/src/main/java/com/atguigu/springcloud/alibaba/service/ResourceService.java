package com.atguigu.springcloud.alibaba.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ResourceService {
    // 这里说几个注意的点：
    // Sentinel Dashboard中，资源名必须和@SentinelResource注解中的value保持一致
    // value：指定资源名，blockHandler：指定降级方法（注意降级方法和目标方法的返回值要一致），方法名一致这个就不用多说了
    @SentinelResource(value = "getResource", blockHandler = "blockHandler")
    public String getResource() {
        return UUID.randomUUID().toString();
    }

    // BlockException一定要带上，否则不进这个方法
    public String blockHandler(BlockException e) {
        e.printStackTrace();
        return "getResource()不可用，请稍后再试";
    }
}
