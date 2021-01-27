package com.leenow.demo.cache;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.leenow.demo.config.properties.AppConfigProperties;
import com.leenow.demo.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author: LeeNow WangLi
 * @date: 2021/1/27 21:33
 * @description:
 */
@Slf4j
public class RedisCacheStore extends AbstractStringCacheStore {


    private static JedisCluster JEDIS;

    public RedisCacheStore(AppConfigProperties configProperties) {
        this.configProperties = configProperties;
        init();
    }

    private void init() {
        JedisPoolConfig cfg = new JedisPoolConfig();
        cfg.setMaxIdle(2);
        cfg.setMaxTotal(30);
        cfg.setMaxWaitMillis(5000);

        Set<HostAndPort> jedisClusterNode = new HashSet<>();
        this.configProperties.getCacheRedisNodes().forEach(node -> {
            String[] split = node.split(":");
            if (split.length > 0) {
                try {
                    jedisClusterNode.add(new HostAndPort(split[0], Integer.parseInt(split[1])));
                } catch (NumberFormatException e) {
                    log.error("could not parse value{} to int", split[1], e);
                }
            }
        });
        if (jedisClusterNode.isEmpty()) {
            jedisClusterNode.add(new HostAndPort("127.0.0.1", 6379));
        }

        JEDIS = new JedisCluster(jedisClusterNode, 5, 20, 3, cfg);
        log.info("Initialized cache redis cluster: {}", JEDIS.getClusterNodes());
    }

    @Override
    Optional<CacheWrapper<String>> getInternal(String key) {
        Assert.hasText(key, "key can't be blank");

        String value = JEDIS.get(key);

        return StrUtil.isBlank(value) ? Optional.empty() : jsonToCacheWrapper(value);
    }

    @Override
    void putInternal(String key, CacheWrapper<String> cacheWrapper) {
        putInternalIfAbsent(key, cacheWrapper);
        try {
            JEDIS.set(key, JsonUtils.objectToJson(cacheWrapper));
            //设置过期时间
            Date expireAt = cacheWrapper.getExpireAt();
            if (expireAt != null) {
                JEDIS.pexpireAt(key, expireAt.getTime());
            }
        } catch (JsonProcessingException e) {
            log.warn("Put cache fail json2object key: [{}] value:[{}]", key, cacheWrapper);
        }
    }

    @Override
    Boolean putInternalIfAbsent(String key, CacheWrapper<String> cacheWrapper) {
        Assert.hasText(key, "Cache key must not be blank");
        Assert.notNull(cacheWrapper, "Cache wrapper must not be null");
        try {
            //存储entry
            if (JEDIS.setnx(key, JsonUtils.objectToJson(cacheWrapper)) <= 0) {
                //存储失败
                log.warn("Failed to put the cache, because the key: [{}] has been present already", key);
                return false;
            }
            //设置过期时间
            Date expireAt = cacheWrapper.getExpireAt();
            if (expireAt != null) {
                JEDIS.pexpireAt(key, expireAt.getTime());
            }
            return true;
        } catch (JsonProcessingException e) {
            log.warn("Put cache fail json2object key: [{}] value:[{}]", key, cacheWrapper);
        }
        log.debug("Cache key: [{}], original cache wrapper: [{}]", key, cacheWrapper);
        return false;
    }

    /**
     * 根据key删除.
     *
     * @param key cache key must not be null
     */
    @Override
    public void delete(String key) {
        Assert.hasText(key, "Cache key must not be blank");

        JEDIS.del(key);

        log.debug("Removed key: [{}]", key);

    }
}
