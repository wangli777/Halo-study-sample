package com.leenow.common.config;


import com.leenow.common.cache.AbstractStringCacheStore;
import com.leenow.common.cache.InMemoryCacheStore;
import com.leenow.common.cache.RedisCacheStore;
import com.leenow.common.config.properties.AppConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LeeNow WangLi
 * @date 2021/1/25 22:22
 * @description
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
            case "redis":
                stringCacheStore = new RedisCacheStore(configProperties);
                break;
            case "memory":
            default:
                stringCacheStore = new InMemoryCacheStore();
                break;
        }
        log.info("cache store load impl : [{}]", stringCacheStore.getClass());
        return stringCacheStore;
    }
}
