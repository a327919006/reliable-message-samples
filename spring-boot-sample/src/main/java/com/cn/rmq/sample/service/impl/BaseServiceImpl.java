package com.cn.rmq.sample.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * <p>Title: BaseServiceImpl</p>
 * <p>Description: 基本服务实现类</p>
 * <p>
 * M mapper
 * T 对象类型
 * PK 主键类型
 *
 * @author Chen Nan
 */
@SuppressWarnings("unchecked")
@Slf4j
public abstract class BaseServiceImpl<M extends Mapper, T, PK> {
    /**
     * 持久层对象
     */
    @Autowired
    protected M mapper;

    public int deleteByPrimaryKey(PK id) {
        return mapper.deleteByPrimaryKey(id);
    }

    public int delete(T record) {
        return mapper.delete(record);
    }

    public int insert(T record) {
        return mapper.insert(record);
    }

    public int insertSelective(T record) {
        return mapper.insertSelective(record);
    }

    public T selectByPrimaryKey(PK id) {
        return (T) mapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(T record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(T record) {
        return mapper.updateByPrimaryKey(record);
    }

    public int count(T record) {
        return mapper.selectCount(record);
    }

    public T get(T record) {
        return (T) mapper.selectOne(record);
    }

    public List<T> list(T record) {
        return mapper.select(record);
    }

    public List<T> listByCondition(Object record) {
        return mapper.selectByExample(record);
    }
}
