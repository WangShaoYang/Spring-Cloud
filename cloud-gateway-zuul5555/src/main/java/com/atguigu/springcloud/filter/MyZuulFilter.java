package com.atguigu.springcloud.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ROUTE_TYPE;

@Component
public class MyZuulFilter extends ZuulFilter {
    /**
     * ERROR_TYPE:出现错误时候执行
     * POST_TYPE:在ROUTE或ERROR执行之后执行
     * PRE_TYPE:在代理执行之前执行
     * ROUTE_TYPE:在代理执行的时候执行
     */
    @Override
    public String filterType() {
        return ROUTE_TYPE;
    }

    /**
     * 如果定义了多个同类型的ZuulFilter，根据filterOrder()方法的返回值，确定执行先后顺序
     * 返回值越小，优先级越高，越先执行
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * true:开启过滤器
     * false:不开启过滤器
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器执行什么操作
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("MyZuulFilterConfig.run");
        return null;
    }
}
