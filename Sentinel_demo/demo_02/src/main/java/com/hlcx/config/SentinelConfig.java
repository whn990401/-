package com.hlcx.config;


import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//注解方式的配置类


@Configuration
public class SentinelConfig {

    @Bean
    public SentinelResourceAspect getSentinelResourceAspect(){
        return new SentinelResourceAspect();
    }
}
