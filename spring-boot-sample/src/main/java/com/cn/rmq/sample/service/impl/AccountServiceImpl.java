package com.cn.rmq.sample.service.impl;

import com.cn.rmq.sample.mapper.AccountMapper;
import com.cn.rmq.sample.model.Constants;
import com.cn.rmq.sample.model.po.Account;
import com.cn.rmq.sample.service.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

/**
 * 帐户服务实现
 *
 * @author Chen Nan
 * @date 2019/3/31.
 */
@Service(timeout = Constants.SERVICE_TIMEOUT)
@Slf4j
public class AccountServiceImpl extends BaseServiceImpl<AccountMapper, Account, Integer>
        implements IAccountService {

    @Override
    public void changeMoney(Account account) {
        mapper.changeMoney(account);
    }
}
