package com.larry.hellobatch.mybatis;

import lombok.Builder;
import lombok.Value;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Value
@Builder
public class PayVo {

    private Long id;
    private Long amount;
    private String txName;
    private LocalDateTime txDateTime;

}
