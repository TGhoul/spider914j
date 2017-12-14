package com.tghoul.web;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author zpj
 * @date 2017/12/14 13:06
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
