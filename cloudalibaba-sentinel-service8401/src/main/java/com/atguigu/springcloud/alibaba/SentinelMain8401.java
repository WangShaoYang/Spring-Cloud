package com.atguigu.springcloud.alibaba;

import com.alibaba.csp.sentinel.transport.config.TransportConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SentinelMain8401 {
    public static void main(String[] args) {
        // Linux下Docker模式运行Sentinel时使用下面这行代码，Windows下运行Sentinel不要使用
        System.setProperty(TransportConfig.HEARTBEAT_CLIENT_IP, "192.168.0.105");
        // 下面这种方式好像不行
        // System.setProperty(TransportConfig.HEARTBEAT_CLIENT_IP, IPUtils.getOutIPV4());
        SpringApplication.run(SentinelMain8401.class, args);
    }
}
