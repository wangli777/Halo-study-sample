package com.leenow.demo.cache;

import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author: LeeNow WangLi
 * @date: 2021/1/25 21:56
 * @description: 存储数据接口，可以是memory内存、redis等实现
 */
public interface CacheStore<K, V> {
    /**
     * get方法，根据key获取value
     *
     * @param key key
     * @return Optional<V> Value
     */
    @NonNull
    Optional<V> get(@NonNull K key);

    /**
     * put方法，设置键值对
     *
     * @param key key
     * @return void
     */
    void put(@NonNull K key, @NonNull V value);

    /**
     * put方法，设置键值对，有过期时间
     *
     * @param key      key
     * @param value    value
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     */
    void put(@NonNull K key, @NonNull V value, long timeout, @NonNull TimeUnit timeUnit);

    /**
     * 如果key不存在，则设置键值对，有过期时间
     *
     * @param key      key
     * @param value    value
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     * @return 是否设置成功，true if the key is absent and the value is set, false if the key is present before, or null if any other reason
     */
    Boolean putIfAbsent(@NonNull K key, @NonNull V value, long timeout, @NonNull TimeUnit timeUnit);

    /**
     * 根据key删除.
     *
     * @param key cache key must not be null
     */
    void delete(@NonNull K key);
}
