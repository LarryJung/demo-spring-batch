package com.larry.hellobatch;

import com.larry.hellobatch.mybatis.PayVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j // log 사용을 위한 lombok 어노테이션
@RequiredArgsConstructor // 생성자 DI를 위한 lombok 어노테이션
@Configuration
public class MybatisJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final SqlSessionFactory sqlSessionFactory;

    private int chunkSize = 3;

    @Bean
    public Job mybatisPagingItemReaderJob() {
        return jobBuilderFactory.get("mybatisPagingItemReaderJob")
                .start(mybatisPagingItemReaderStep())
                .build();
    }

    @Bean
    public Step mybatisPagingItemReaderStep() {
        return stepBuilderFactory.get("mybatisPagingItemReaderStep")
                .<PayVo, PayVo>chunk(chunkSize)
                .reader(mybatisPagingItemReader())
                .processor(mybaisItemProcessor())
                .writer(mybatisPagingItemWriter())
                .build();
    }

    @Bean
    public MyBatisPagingItemReader<PayVo> mybatisPagingItemReader() {
        return new MyBatisPagingItemReaderBuilder<PayVo>()
                .sqlSessionFactory(sqlSessionFactory)
                .queryId("com.larry.hellobatch.mybatis.PayMapper.selectAll")
                .pageSize(chunkSize)
                .build();
    }

    @Bean
    public ItemProcessor<PayVo, PayVo> mybaisItemProcessor() {
        return pay -> {
            log.info("pay ? = {}", pay);
            return PayVo.builder()
                    .id(pay.getId())
                    .amount(pay.getAmount())
                    .txName(pay.getTxName() + "_updated2")
                    .build();
        };
    }

    @Bean
    public MyBatisBatchItemWriter<PayVo> mybatisPagingItemWriter() {
        return new MyBatisBatchItemWriterBuilder<PayVo>()
                .sqlSessionFactory(sqlSessionFactory)
                .statementId("com.larry.hellobatch.mybatis.PayMapper.update")
                .build();
    }

}
