package com.cn.rmq.sample.service.impl;

import cn.hutool.json.JSONUtil;
import com.cn.rmq.api.service.IRmqService;
import com.cn.rmq.sample.mapper.PayOrderMapper;
import com.cn.rmq.sample.model.Constants;
import com.cn.rmq.sample.model.dto.PayDto;
import com.cn.rmq.sample.model.po.PayOrder;
import com.cn.rmq.sample.service.IPayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.transaction.annotation.Transactional;

/**
 * 支付订单服务实现
 *
 * @author Chen Nan
 * @date 2019/3/31.
 */
@Service(timeout = Constants.SERVICE_TIMEOUT)
@Slf4j
public class PayOrderServiceImpl extends BaseServiceImpl<PayOrderMapper, PayOrder, Integer>
        implements IPayOrderService {

    @Reference
    private IRmqService rmqService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void paySuccess(PayDto req) {
        log.info("【paySuccess】start:", req);
        PayOrder payOrder = mapper.selectByPrimaryKey(req.getPayOrderId());
        if (payOrder == null) {
            throw new RuntimeException("支付订单不存在");
        }

        if (payOrder.getStatus() == 1) {
            log.info("【paySuccess】该订单已处理, payOrder={}", payOrder);
            return;
        }

        if (payOrder.getStatus() != 0) {
            log.info("【paySuccess】订单状态非待支付, payOrder={}", payOrder);
            return;
        }

        // 调用RMQ，创建预发送消息
        String messageId = rmqService.createPreMessage(Constants.QUEUE_PAY, JSONUtil.toJsonStr(payOrder));

        // 执行业务
        payOrder = new PayOrder();
        payOrder.setId(req.getPayOrderId());
        payOrder.setStatus((byte) 1);
        mapper.updateByPrimaryKeySelective(payOrder);

        // 异步调用RMQ，确认发送消息
        // 假如使用同步调用，RMQ确认消息成功，已经把MQ消息发送给下游子系统
        // 但是由于网络波动或其他原因，此处抛出异常，使已经正常执行的业务回滚，如果业务复杂，就会造成数据不一致。
        // 因此使用异步调用，忽略确认发送消息的异常。异常结果由消息确认子系统来处理。
        RpcContext.getContext().asyncCall(() -> rmqService.confirmAndSendMessage(messageId));

        log.info("【paySuccess】success, payOrderId={}, messageId={}", req.getPayOrderId(), messageId);
    }

    @Override
    public int check(PayOrder req) {
        log.info("【payCheck】start, PayOrderId={}", req.getId());
        PayOrder payOrder = mapper.selectByPrimaryKey(req.getId());
        if (payOrder == null) {
            log.info("【payCheck】订单不存在, PayOrderId={}", req.getId());
            return 0;
        }
        log.info("【payCheck】success, PayOrderId={}, status={}", req.getId(), payOrder.getStatus());
        return payOrder.getStatus();
    }
}
