package com.leenow.common.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import javax.annotation.PreDestroy;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author bg395819 WangLi
 * @date 21/1/26 13:41
 * @description
 */
@Slf4j
public class InMemoryCacheStore extends AbstractStringCacheStore {

    /**
     * Cleaner schedule period. (ms)
     */
    private static final long PERIOD = 60 * 1000;
    /**
     * Cache container.
     */
    private static final ConcurrentHashMap<String, CacheWrapper<String>> CACHE_CONTAINER = new ConcurrentHashMap<>();

    private final Lock LOCK = new ReentrantLock();

    private final ScheduledExecutorService executorService;

    public InMemoryCacheStore() {
        executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("cacheStore-cleaner-schedule-pool-%d").daemon(true).build());

        executorService.scheduleAtFixedRate(() -> CACHE_CONTAINER.keySet().forEach(key -> {
                    log.debug("cacheStore-cleaner-schedule start work");
                    if (!InMemoryCacheStore.this.get(key).isPresent()) {
                        log.debug("Deleted the cache: [{}] for expiration", key);
                    }
                }), 0, PERIOD, TimeUnit.MILLISECONDS
        );

    }

    @Override
    Optional<CacheWrapper<String>> getInternal(@NonNull String key) {
        Assert.hasText(key, "Cache key must not be blank");

        return Optional.ofNullable(CACHE_CONTAINER.get(key));
    }

    @Override
    void putInternal(String key, CacheWrapper<String> value) {
        Assert.hasText(key, "Cache key must not be blank");
        Assert.notNull(value, "Cache value must not be null");
        CacheWrapper<String> result = CACHE_CONTAINER.put(key, value);
        log.debug("InMemory Cache put a entry, key:{},value:{};result:{}", key, value, result);
    }

    @Override
    Boolean putInternalIfAbsent(String key, CacheWrapper<String> value) {
        Assert.hasText(key, "Cache key must not be blank");
        Assert.notNull(value, "Cache value must not be null");

        LOCK.lock();
        try {
            Optional<String> optional = get(key);
            if (optional.isPresent()) {
                log.warn("Failed to put the cache, because cache key[{}] already exist.", key);
                return false;
            }
            putInternal(key, value);
            log.debug("Put successfully");
            return true;
        } finally {
            LOCK.unlock();
        }
    }

    @Override
    public void delete(String key) {
        Assert.hasText(key, "Cache key must not be blank");
        CACHE_CONTAINER.remove(key);
        log.debug("Removed key: [{}]", key);
    }

    @PreDestroy
    public void preDestroy() {
        log.debug("Cancelling cacheStore-cleaner-schedule tasks");
        executorService.shutdown();
        clear();
    }

    private void clear() {
        CACHE_CONTAINER.clear();
    }
}
