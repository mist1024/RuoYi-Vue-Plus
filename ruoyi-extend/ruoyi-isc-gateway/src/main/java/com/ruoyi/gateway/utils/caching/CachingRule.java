package com.ruoyi.gateway.utils.caching;

import com.ruoyi.gateway.utils.beans.IscRule;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author Wenchao Gong
 * @date 2021-10-22
 */
public class CachingRule {
    public static final String RULE_CACHE_NAME = "RULE_CACHE";
    public static final Cache RULE_CACHE = new ConcurrentMapCache(RULE_CACHE_NAME);

    /**
     * 通过 Key 获取缓存中的 Rule
     *
     * @param key    缓存key
     * @param mapper 如果缓存中没有 key 从mapper 中获取
     * @return
     */
    public static IscRule getRule(String key, Function<String, IscRule> mapper) {
        Cache.ValueWrapper value = RULE_CACHE.get(key);
        if (Objects.nonNull(value)) {
            return (IscRule) value.get();
        }
        IscRule rule = mapper.apply(key);
        RULE_CACHE.put(key, rule);
        return rule;
    }

    /**
     * 清除 缓存 如果key存在
     *
     * @param key 缓存key
     * @return 是否清除
     */
    public static boolean evict(String key) {
        return RULE_CACHE.evictIfPresent(key);
    }

    /**
     * 清除所有缓存 一刷新整个缓存
     * @return
     */
    public static boolean refresh() {
        RULE_CACHE.clear();
        return true;
    }
}
