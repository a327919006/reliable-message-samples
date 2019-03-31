package com.cn.rmq.sample.model.po;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "t_pay_order")
public class PayOrder {
    /**
     * 支付订单ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 业务系统订单ID
     */
    @Column(name = "business_order_id")
    private Integer businessOrderId;

    /**
     * 支付金额
     */
    private BigDecimal money;

    /**
     * 支付状态 0未支付 1已支付
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