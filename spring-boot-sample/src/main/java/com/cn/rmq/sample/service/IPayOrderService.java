package com.cn.rmq.sample.service;

import com.cn.rmq.sample.model.dto.PayDto;
import com.cn.rmq.sample.model.po.PayOrder;

/**
 * 支付订单服务接口
 *
 * @author Chen Nan
 */
public interface IPayOrderService extends IBaseService<PayOrder, Integer> {
    /**
     * 支付成功
     * @param req 支付订单ID
     */
    void paySuccess(PayDto req);

    /**
     * 确认业务处理结果
     * @param req 支付订单信息
     * @return 是否成功 1是 0否
     */
    int check(PayOrder req);
}
