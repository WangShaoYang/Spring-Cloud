package com.springcloud.alibaba.service.impl;

import com.springcloud.alibaba.dao.StorageDao;
import com.springcloud.alibaba.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {
    @Resource
    private StorageDao storageDao;

    @Override
    public void decrease(Long productId, Integer count) {
        log.info("----> StorageService中扣减库存");
        storageDao.decrease(productId, count);
        log.info("----> StorageService中扣减库存完成");
    }
}
