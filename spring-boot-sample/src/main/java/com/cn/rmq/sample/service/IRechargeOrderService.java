package com.cn.rmq.sample.service;

import com.cn.rmq.sample.model.dto.RechargeDto;
import com.cn.rmq.sample.model.po.PayOrder;
import com.cn.rmq.sample.model.po.RechargeOrder;

/**
 * 充值订单服务接口
 *
 * @author Chen Nan
 */
public interface IRechargeOrderService extends IBaseService<RechargeOrder, Integer> {
    /**
     * 创建充值订单
     * @param req 充值账户与金额
     * @return 支付订单ID
     */
    Integer createRechargeOrder(RechargeDto req);

    /**
     * 充值成功业务处理
     * @param payOrder 支付订单信息
     */
    void rechargeSuccess(PayOrder payOrder);
}
