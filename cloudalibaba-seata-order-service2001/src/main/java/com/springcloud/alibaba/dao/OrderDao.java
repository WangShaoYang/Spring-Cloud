package com.springcloud.alibaba.dao;

import com.springcloud.alibaba.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {
    /**
     * 新建订单
     */
    int create(Order order);

    /**
     * 修改订单状态，从0改为1
     */
    int update(@Param("id") Long id, @Param("status") Integer status);
}
