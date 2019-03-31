package com.cn.rmq.sample.mq;

import cn.hutool.json.JSONUtil;
import com.cn.rmq.api.model.RmqMessage;
import com.cn.rmq.api.service.IRmqService;
import com.cn.rmq.sample.model.Constants;
import com.cn.rmq.sample.model.po.PayOrder;
import com.cn.rmq.sample.service.IRechargeOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author Chen Nan
 * @date 2019/3/31.
 */
@Component
@Slf4j
public class PayQueueCustomer {

    @Reference
    private IRechargeOrderService rechargeOrderService;
    @Reference
    private IRmqService rmqService;

    @JmsListener(destination = Constants.QUEUE_PAY)
    public void handleMsg(RmqMessage msg) {
        try {
            log.info("【payQueue】开始处理消息" + msg);
            PayOrder payOrder = JSONUtil.toBean(msg.getMessageBody(), PayOrder.class);

            rechargeOrderService.rechargeSuccess(payOrder);
            rmqService.deleteMessageById(msg.getMessageId());

            log.info("【payQueue】处理消息成功");
        } catch (Exception e) {
            log.error("【payQueue】Exception:", e);
        }
    }
}
