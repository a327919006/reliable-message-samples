package com.cn.rmq.sample.mapper;

import com.cn.rmq.sample.model.po.Account;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Chen Nan
 * @date 2019/3/31.
 */
public interface AccountMapper extends Mapper<Account> {
    /**
     * 修改账户金额
     * @param account 账户信息
     */
    void changeMoney(Account account);
}