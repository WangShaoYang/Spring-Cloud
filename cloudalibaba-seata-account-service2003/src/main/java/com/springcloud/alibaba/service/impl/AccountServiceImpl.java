package com.springcloud.alibaba.service.impl;

import com.springcloud.alibaba.dao.AccountDao;
import com.springcloud.alibaba.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountDao accountDao;

    @Override
    public void decrease(Long userId, BigDecimal money) {
        log.info("---> AccountService中扣减账户余额");
        // try {
        //     Thread.sleep(5000); // sleep5秒
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        accountDao.decrease(userId, money);
        log.info("---> AccountService中扣减账户余额完成");
    }
}
