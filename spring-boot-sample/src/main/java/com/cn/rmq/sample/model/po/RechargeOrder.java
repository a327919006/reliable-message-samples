package com.cn.rmq.sample.model.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "t_recharge_order")
public class RechargeOrder implements Serializable {
    /**
     * 充值记录ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 账户ID
     */
    @Column(name = "account_id")
    private Integer accountId;

    /**
     * 支付流水ID
     */
    @Column(name = "pay_order_id")
    private Integer payOrderId;

    /**
     * 充值金额
     */
    private BigDecimal money;

    /**
     * 0待支付 1已支付
     */
    private Byte status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
}