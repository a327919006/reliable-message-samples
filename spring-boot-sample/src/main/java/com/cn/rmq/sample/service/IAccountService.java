package com.cn.rmq.sample.service;

import com.cn.rmq.sample.model.po.Account;

/**
 * 账户服务接口
 *
 * @author Chen Nan
 */
public interface IAccountService extends IBaseService<Account, Integer> {
    /**
     * 修改账户金额
     * @param account 账户信息
     */
    void changeMoney(Account account);
}
