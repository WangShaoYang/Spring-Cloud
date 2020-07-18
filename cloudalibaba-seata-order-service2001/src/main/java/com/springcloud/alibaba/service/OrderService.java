package com.springcloud.alibaba.service;

import com.springcloud.alibaba.domain.Order;

public interface OrderService {
    /**
     * 创建订单
     */
    void create(Order order);
}
