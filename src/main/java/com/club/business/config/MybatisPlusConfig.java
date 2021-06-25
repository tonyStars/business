package com.club.business.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatisplus配置
 * @author Tom
 * @date 2019-11-16
 */
@Configuration
@MapperScan("com.club.business.**.dao")
public class MybatisPlusConfig {

    /**
     * 分页插件
     * @return 分页bean
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}