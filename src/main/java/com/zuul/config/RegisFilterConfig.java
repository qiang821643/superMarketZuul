package com.zuul.config;

import com.zuul.filter.UnifiedDataProceFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: create QiangShW
 * @version: v1.0
 * @description: com.zuul.config
 * @date:2019/7/6
 **/
@Configuration
public class RegisFilterConfig {

    /**
     * 返回数据统一处理过滤器 顺序是最后1位
     * @return
     */
    @Bean
    public UnifiedDataProceFilter resopnseFilter(){
       return new UnifiedDataProceFilter();
    }
}
