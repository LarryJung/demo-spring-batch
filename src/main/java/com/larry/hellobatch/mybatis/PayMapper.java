package com.larry.hellobatch.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PayMapper {

    @SelectProvider(type = SqlUtils.class, method = "buildPaySelectAll")
    PayVo selectAll();

    @SelectProvider(type = SqlUtils.class, method = "updatePay")
    void update(PayVo vo);
}
