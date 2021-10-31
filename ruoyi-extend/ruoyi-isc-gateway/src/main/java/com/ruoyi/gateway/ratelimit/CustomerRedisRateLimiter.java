package com.ruoyi.gateway.ratelimit;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.gateway.utils.GatewayUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.support.ConfigurationService;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Wenchao Gong
 * @date 2021-10-16
 */
@Slf4j
public class CustomerRedisRateLimiter extends RedisRateLimiter {

    private final ReactiveStringRedisTemplate redisTemplate;

    private final RedisScript<List<Long>> script;
    private final RedisScript<Long> timeRedisScript;

    private final AtomicBoolean initialized = new AtomicBoolean(false);

    public CustomerRedisRateLimiter(ReactiveStringRedisTemplate redisTemplate, RedisScript<List<Long>> script,
                                    ConfigurationService configurationService, DefaultRedisScript<Long> timeRedisScript) {
        super(redisTemplate, script, configurationService);
        this.redisTemplate = redisTemplate;
        this.script = script;
        this.timeRedisScript = timeRedisScript;
        this.initialized.compareAndSet(false, true);
    }

    public Mono<Response> isAllowed(String routeId, String ak, Long replenishRate, TimeUnit timeUnit) {
        Config routeConfig = new Config().setReplenishRate(replenishRate.intValue());
        String id = GatewayUtils.getRouteKey(ak, routeId);
        int time = 60;
        LocalDateTime now = LocalDateTime.now();
        List<String> keys;
        switch (timeUnit) {
            case DAYS:
                time += LocalDateTimeUtil.between(now, LocalDateTime.now().with(LocalTime.MAX), ChronoUnit.SECONDS);
                keys = getKeys(id, now.getDayOfMonth(), timeUnit);
                break;
            case HOURS:
                time += (60 - now.getMinute()) * 60 - now.getSecond();
                keys = getKeys(id, now.getHour(), timeUnit);
                break;
            case MINUTES:
                time += 60 - now.getSecond();
                keys = getKeys(id, now.getMinute(), timeUnit);
                break;
            case SECONDS:
                return isAllowed(routeConfig, id);
            default:
                return Mono.just(new Response(true, getHeaders(routeConfig, -1L)));
        }
        return isAllowed(routeConfig, keys, time, timeUnit);
    }

    public Mono<Response> isAllowed(Config routeConfig, String id) {
        if (!this.initialized.get()) {
            throw new IllegalStateException("RedisRateLimiter is not initialized");
        }

        // How many requests per second do you want a user to be allowed to do?
        int replenishRate = routeConfig.getReplenishRate();

        // How much bursting do you want to allow?
        int burstCapacity = routeConfig.getBurstCapacity();

        // How many tokens are requested per request?
        int requestedTokens = routeConfig.getRequestedTokens();

        try {
            List<String> keys = getKeys(id);

            // The arguments to the LUA script. time() returns unixtime in seconds.
            List<String> scriptArgs = Arrays.asList(replenishRate + "", burstCapacity + "",
                    Instant.now().getEpochSecond() + "", requestedTokens + "");
            // allowed, tokens_left = redis.eval(SCRIPT, keys, args)
            Flux<List<Long>> flux = this.redisTemplate.execute(this.script, keys, scriptArgs);
            // .log("redisratelimiter", Level.FINER);
            return flux.onErrorResume(throwable -> {
                if (log.isDebugEnabled()) {
                    log.debug("Error calling rate limiter lua", throwable);
                }
                return Flux.just(Arrays.asList(1L, -1L));
            }).reduce(new ArrayList<Long>(), (longs, l) -> {
                longs.addAll(l);
                return longs;
            }).map(results -> {
                boolean allowed = results.get(0) == 1L;
                Long tokensLeft = results.get(1);

                Response response = new Response(allowed, getHeaders(routeConfig, tokensLeft));

                if (log.isDebugEnabled()) {
                    log.debug("response: " + response);
                }
                return response;
            });
        }
        catch (Exception e) {
            /*
             * We don't want a hard dependency on Redis to allow traffic. Make sure to set
             * an alert so you know if this is happening too much. Stripe's observed
             * failure rate is 0.01%.
             */
            log.error("Error determining if user allowed from redis", e);
        }
        return Mono.just(new Response(true, getHeaders(routeConfig, -1L)));
    }

    public Mono<Response> isAllowed(Config routeConfig, List<String> keys, int time, TimeUnit timeUnit) {
        if (!this.initialized.get()) {
            throw new IllegalStateException("RedisRateLimiter is not initialized");
        }

        // How many requests per second do you want a user to be allowed to do?
        int replenishRate = routeConfig.getReplenishRate();

        // How much bursting do you want to allow?
        int burstCapacity = routeConfig.getBurstCapacity();

        // How many tokens are requested per request?
        int requestedTokens = routeConfig.getRequestedTokens();

        try {
            // The arguments to the LUA script. time() returns unixtime in seconds.
            List<String> scriptArgs = Arrays.asList(replenishRate + "", time + "");
            // allowed, tokens_left = redis.eval(SCRIPT, keys, args)
            Flux<Long> flux = this.redisTemplate.execute(this.timeRedisScript, keys, scriptArgs);
            // .log("redisratelimiter", Level.FINER);
            return flux.next().onErrorResume(throwable -> {
                if (log.isDebugEnabled()) {
                    log.debug("Error calling rate limiter lua", throwable);
                }
                return Mono.just(-1L);
            }).map(tokensLeft -> {
                boolean allowed = tokensLeft > 0 && tokensLeft < replenishRate;
                Response response = new Response(allowed, getHeaders(routeConfig, tokensLeft, timeUnit));
                if (log.isDebugEnabled()) {
                    log.debug("response: " + response);
                }
                return response;
            });
        }
        catch (Exception e) {
            /*
             * We don't want a hard dependency on Redis to allow traffic. Make sure to set
             * an alert so you know if this is happening too much. Stripe's observed
             * failure rate is 0.01%.
             */
            log.error("Error determining if user allowed from redis", e);
        }
        return Mono.just(new Response(true, getHeaders(routeConfig, -1L, timeUnit)));
    }

    public Map<String, String> getHeaders(Config config, Long tokensLeft, TimeUnit timeUnit) {
        Map<String, String> headers = new HashMap<>();
        if (isIncludeHeaders()) {
            String suffix = StrUtil.upperFirst(timeUnit.name().toLowerCase());
            headers.put(this.getRemainingHeader() + '-' + suffix, tokensLeft.toString());
            headers.put(this.getReplenishRateHeader() + '-' + suffix, String.valueOf(config.getReplenishRate()));
        }
        return headers;
    }

    static List<String> getKeys(String id) {
        // use `{}` around keys to use Redis Key hash tags
        // this allows for using redis cluster

        // Make a unique key per user.
        String prefix = "rate_limiter.{" + id;

        // You need two Redis keys for Token Bucket.
        String tokenKey = prefix + "}.tokens";
        String timestampKey = prefix + "}.timestamp";
        return Arrays.asList(tokenKey, timestampKey);
    }

    static List<String> getKeys(String id, int now, TimeUnit timeUnit) {
        String tokenKey = "rate_limiter.{" + id + ':' + now + "}." + timeUnit.name();
        return Arrays.asList(tokenKey);
    }
}
