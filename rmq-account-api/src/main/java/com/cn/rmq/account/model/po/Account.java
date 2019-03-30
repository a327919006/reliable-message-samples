package com.cn.rmq.account.model.po;

import lombok.Data;

import javax.persistence.Id;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author Chen Nan
 * @date 2019/3/30.
 */
@Data
public class Account {
    @Id
    private Integer id;
    private Double money;
}
