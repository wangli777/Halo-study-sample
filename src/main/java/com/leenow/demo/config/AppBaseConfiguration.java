package com.leenow.demo.config;


import com.leenow.demo.cache.AbstractStringCacheStore;
import com.leenow.demo.cache.InMemoryCacheStore;
import com.leenow.demo.config.properties.AppConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: LeeNow WangLi
 * @date: 2021/1/25 22:22
 * @description:
 */
@Configuration
@EnableConfigurationProperties(AppConfigProperties.class)
@Slf4j
public class AppBaseConfiguration {

    @Autowired
    private AppConfigProperties configProperties;


    @Bean
    @ConditionalOnMissingBean
    public AbstractStringCacheStore stringCacheStore() {
        AbstractStringCacheStore stringCacheStore;
        switch (configProperties.getCache()) {
//            case "redis":
//                stringCacheStore = new RedisCacheStore();
            case "memory":
            default:
                stringCacheStore = new InMemoryCacheStore();
                break;
        }
        log.info("cache store load impl : [{}]", stringCacheStore.getClass());
        return stringCacheStore;
    }
}
