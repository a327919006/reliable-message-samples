package com.cn.rmq.sample.controller;

import com.cn.rmq.sample.model.dto.BaseRsp;
import com.cn.rmq.sample.model.dto.RechargeDto;
import com.cn.rmq.sample.service.IRechargeOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Chen Nan
 */
@RestController
@Api(tags = "充值订单", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value = "/recharge")
@Slf4j
public class RechargeController {

    @Reference
    private IRechargeOrderService rechargeOrderService;


    @ApiOperation("创建充值订单")
    @PostMapping
    public Object add(@ModelAttribute @Valid RechargeDto req) {
        BaseRsp rsp = new BaseRsp();
        Integer payOrderId = rechargeOrderService.createRechargeOrder(req);
        rsp.setData(payOrderId);
        return rsp;
    }
}
