package com.leenow.demo.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: LeeNow WangLi
 * @date: 2021/1/25 22:15
 * @description:
 */
@Data
@ConfigurationProperties("base")
public class AppConfigProperties {
    /**
     * cacheStore 实现
     * memory default
     * redis
     */
    private String cache = "memory";
}
