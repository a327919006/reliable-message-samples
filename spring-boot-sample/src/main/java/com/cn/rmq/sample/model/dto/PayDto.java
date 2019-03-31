package com.cn.rmq.sample.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author Chen Nan
 * @date 2019/3/31.
 */
@ApiModel
@Data
public class PayDto implements Serializable {
    @ApiModelProperty(value = "支付订单ID", required = true)
    @NotNull
    @Min(1)
    private Integer payOrderId;
}
