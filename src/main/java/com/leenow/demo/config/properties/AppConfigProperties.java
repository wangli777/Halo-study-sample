package com.leenow.demo.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LeeNow WangLi
 * @date 2021/1/25 22:15
 * @description
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

    /**
     * Authentication enabled
     */
    private boolean authEnabled = true;

    /**
     * redis集群
     */
    private List<String> cacheRedisNodes = new ArrayList<>();

    /**
     * redis密码
     */
    private String cacheRedisPassword = "";


}
