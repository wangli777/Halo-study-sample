package com.leenow.demo.cache;

import com.leenow.demo.config.properties.AppConfigProperties;
import com.leenow.demo.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author LeeNow WangLi
 * @date 2021/1/25 22:12
 * @description
 */
@Slf4j
public abstract class AbstractCacheStore<K, V> implements CacheStore<K, V> {

    protected AppConfigProperties configProperties;

    @Override
    public Optional<V> get(K key) {
        return getInternal(key).map(wrapper -> {
            if (wrapper.getExpireAt() != null && wrapper.getExpireAt().before(DateUtils.now())) {
                // Expired then delete it
                log.warn("Cache key: [{}] has been expired", key);
                delete(key);

                return null;
            }
            return wrapper.getData();
        });
    }

    @Override
    public void put(K key, V value) {
        putInternal(key, buildCacheWrapper(value, 0, null));
    }

    @Override
    public void put(K key, V value, long timeout, TimeUnit timeUnit) {
        putInternal(key, buildCacheWrapper(value, timeout, timeUnit));
    }

    @Override
    public Boolean putIfAbsent(K key, V value, long timeout, TimeUnit timeUnit) {
        return putInternalIfAbsent(key, buildCacheWrapper(value, timeout, timeUnit));
    }

    abstract Optional<CacheWrapper<V>> getInternal(@NonNull K key);

    abstract void putInternal(@NonNull K key, @NonNull CacheWrapper<V> cacheWrapper);

    abstract Boolean putInternalIfAbsent(@NonNull K key, @NonNull CacheWrapper<V> cacheWrapper);


    @NonNull
    private CacheWrapper<V> buildCacheWrapper(@NonNull V value, long timeout, TimeUnit timeUnit) {
        Assert.notNull(value, "Cache value must not be null");
        Assert.isTrue(timeout >= 0, "Cache expiration timeout must not be less than 1");

        Date now = DateUtils.now();

        Date expireAt = null;

        if (timeout > 0 && timeUnit != null) {
            expireAt = DateUtils.add(now, timeout, timeUnit);
        }

        // Build cache wrapper
        CacheWrapper<V> cacheWrapper = new CacheWrapper<>();
        cacheWrapper.setCreateAt(now);
        cacheWrapper.setExpireAt(expireAt);
        cacheWrapper.setData(value);

        return cacheWrapper;
    }


}
