package com.cn.rmq.sample.service.impl;

import com.cn.rmq.sample.mapper.RechargeOrderMapper;
import com.cn.rmq.sample.model.Constants;
import com.cn.rmq.sample.model.dto.RechargeDto;
import com.cn.rmq.sample.model.po.Account;
import com.cn.rmq.sample.model.po.PayOrder;
import com.cn.rmq.sample.model.po.RechargeOrder;
import com.cn.rmq.sample.service.IAccountService;
import com.cn.rmq.sample.service.IPayOrderService;
import com.cn.rmq.sample.service.IRechargeOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 充值订单服务实现
 *
 * @author Chen Nan
 * @date 2019/3/31.
 */
@Service(timeout = Constants.SERVICE_TIMEOUT)
@Slf4j
public class RechargeOrderServiceImpl extends BaseServiceImpl<RechargeOrderMapper, RechargeOrder, Integer>
        implements IRechargeOrderService {

    @Reference
    private IPayOrderService payOrderService;

    @Reference
    private IAccountService accountService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createRechargeOrder(RechargeDto req) {
        log.info("【createRecharge】start:{}", req);
        Date now = new Date();

        RechargeOrder rechargeOrder = new RechargeOrder();
        BeanUtils.copyProperties(req, rechargeOrder);
        rechargeOrder.setCreateTime(now);
        rechargeOrder.setUpdateTime(now);
        mapper.insertSelective(rechargeOrder);

        log.info("rechargeOrder=" + rechargeOrder);

        PayOrder payOrder = new PayOrder();
        payOrder.setBusinessOrderId(rechargeOrder.getId());
        payOrder.setMoney(rechargeOrder.getMoney());
        payOrder.setCreateTime(now);
        payOrder.setUpdateTime(now);
        payOrderService.insertSelective(payOrder);

        log.info("【createRecharge】success, payOrder={}", payOrder);
        return payOrder.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rechargeSuccess(PayOrder payOrder) {
        log.info("【rechargeSuccess】start, payOrder={}", payOrder);

        RechargeOrder rechargeOrder = mapper.selectByPrimaryKey(payOrder.getBusinessOrderId());
        if (rechargeOrder == null) {
            log.error("【rechargeSuccess】充值订单不存在, payOrder={}", payOrder);
            throw new RuntimeException("充值订单不存在");
        }

        if (rechargeOrder.getStatus() == 1) {
            log.info("【rechargeSuccess】该订单已处理, payOrder={}", payOrder);
            return;
        }

        if (rechargeOrder.getStatus() != 0) {
            log.info("【rechargeSuccess】订单状态非待支付, payOrder={}", payOrder);
            return;
        }

        // 修改充值订单状态
        RechargeOrder updateRechargeOrder = new RechargeOrder();
        updateRechargeOrder.setId(payOrder.getBusinessOrderId());
        updateRechargeOrder.setPayOrderId(payOrder.getId());
        updateRechargeOrder.setStatus((byte) 1);
        updateRechargeOrder.setUpdateTime(new Date());
        mapper.updateByPrimaryKeySelective(updateRechargeOrder);


        Account account = accountService.selectByPrimaryKey(rechargeOrder.getAccountId());
        if(account == null){
            log.error("【rechargeSuccess】账户不存在, payOrder={}", payOrder);
            throw new RuntimeException("账户不存在");
        }

        //增加账户余额
        Account updateAccount = new Account();
        updateAccount.setId(rechargeOrder.getAccountId());
        updateAccount.setMoney(payOrder.getMoney());
        accountService.changeMoney(updateAccount);
    }
}
