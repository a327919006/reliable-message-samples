package com.cn.rmq.sample.controller;

import com.cn.rmq.sample.model.dto.BaseRsp;
import com.cn.rmq.sample.model.dto.PayDto;
import com.cn.rmq.sample.model.dto.RechargeDto;
import com.cn.rmq.sample.model.po.PayOrder;
import com.cn.rmq.sample.service.IPayOrderService;
import com.cn.rmq.sample.service.IRechargeOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Chen Nan
 */
@RestController
@Api(tags = "支付订单", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value = "pay")
@Slf4j
public class PayController {

    @Reference
    private IPayOrderService payOrderService;

    /**
     * 模拟银行支付回调
     * @param req 订单ID
     * @return 业务处理结果
     */
    @ApiOperation("模拟银行支付回调")
    @PostMapping("success")
    public Object add(@ModelAttribute @Valid PayDto req) {
        BaseRsp rsp = new BaseRsp();
        payOrderService.paySuccess(req);
        return rsp;
    }

    /**
     * 本接口提供给RMQ消息确认子系统，确认业务是否处理正常，是否发送消息
     * @param req 支付订单信息
     * @return 业务处理结果 1成功 0失败
     */
    @ApiOperation("消息确认子系统-确认是否发送消息")
    @PostMapping("check")
    public Object check(@RequestBody @Valid PayOrder req) {
        BaseRsp rsp = new BaseRsp();
        int result = payOrderService.check(req);
        rsp.setData(result);
        return rsp;
    }
}
