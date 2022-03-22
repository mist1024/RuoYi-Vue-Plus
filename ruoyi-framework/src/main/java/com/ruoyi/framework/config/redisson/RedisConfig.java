package com.ruoyi.framework.config.redisson;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.properties.RedissonProperties;
import com.ruoyi.framework.config.properties.RedissonProperties.ClusterServersConfig;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.DefaultNameMapper;
import org.redisson.api.NameMapper;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * redis配置
 *
 * @author Lion Li
 */
@Slf4j
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    private static final String REDIS_PROTOCOL_PREFIX = "redis://";
    private static final String REDISS_PROTOCOL_PREFIX = "rediss://";

    @Resource
    private RedisProperties redisProperties;

    @Resource
    private RedissonProperties redissonProperties;

    /**
     * 单节点模式
     */
    @Primary
    @Bean(name = "redissonClient", destroyMethod = "shutdown")
    @ConditionalOnProperty(name = "redisson.mode", havingValue = RedissonProperties.MODE_SINGLE)
    @ConditionalOnMissingBean(RedissonClient.class)
    RedissonClient single() {
        RedissonProperties.SingleServerConfig singleServerConfig = redissonProperties.getSingleServerConfig();
        if (ObjectUtil.isNull(singleServerConfig)) {
            throw new IllegalArgumentException("Redis connection 'redisson.singleServerConfig' is empty");
        }
        Config config = getConfig();
        // 单机模式
        config.useSingleServer()
            .setNameMapper(getNameMapper())
            .setAddress(getRedisProtocolPrefix() + redisProperties.getHost() + ":" + redisProperties.getPort())
            .setConnectTimeout(((Long) redisProperties.getTimeout().toMillis()).intValue())
            .setDatabase(redisProperties.getDatabase())
            .setPassword(StringUtils.isNotBlank(redisProperties.getPassword()) ? redisProperties.getPassword() : null)
            .setTimeout(singleServerConfig.getTimeout())
            .setRetryAttempts(singleServerConfig.getRetryAttempts())
            .setRetryInterval(singleServerConfig.getRetryInterval())
            .setClientName(singleServerConfig.getClientName())
            .setIdleConnectionTimeout(singleServerConfig.getIdleConnectionTimeout())
            .setSubscriptionConnectionPoolSize(singleServerConfig.getSubscriptionConnectionPoolSize())
            .setConnectionMinimumIdleSize(singleServerConfig.getConnectionMinimumIdleSize())
            .setConnectionPoolSize(singleServerConfig.getConnectionPoolSize());
        log.info("初始化 redis 配置: [mode={}, address={}]", redissonProperties.getMode(), redisProperties.getHost() + ":" + redisProperties.getPort());
        return Redisson.create(config);
    }

    /**
     * 哨兵模式
     */
    @Primary
    @Bean(name = "redissonClient", destroyMethod = "shutdown")
    @ConditionalOnProperty(name = "redisson.mode", havingValue = RedissonProperties.MODE_SENTINEL)
    @ConditionalOnMissingBean(RedissonClient.class)
    RedissonClient sentinel() {
        RedissonProperties.ClusterServersConfig clusterServersConfig = getClusterServersConfig();
        String finalPrefix = getRedisProtocolPrefix();
        List<String> nodes = redisProperties.getSentinel().getNodes()
            .stream()
            .map(node -> finalPrefix + node)
            .collect(Collectors.toList());
        Config config = getConfig();
        // 哨兵方式
        config.useSentinelServers()
            .setCheckSentinelsList(false)
            .setNameMapper(getNameMapper())
            .setConnectTimeout(((Long) redisProperties.getTimeout().toMillis()).intValue())
            .setPassword(StringUtils.isNotBlank(redisProperties.getPassword()) ? redisProperties.getPassword() : null)
            .setMasterName(redisProperties.getSentinel().getMaster())
            .setTimeout(clusterServersConfig.getTimeout())
            .setRetryAttempts(clusterServersConfig.getRetryAttempts())
            .setRetryInterval(clusterServersConfig.getRetryInterval())
            .setClientName(clusterServersConfig.getClientName())
            .setIdleConnectionTimeout(clusterServersConfig.getIdleConnectionTimeout())
            .setPingConnectionInterval(clusterServersConfig.getPingConnectionInterval())
            .setSubscriptionConnectionPoolSize(clusterServersConfig.getSubscriptionConnectionPoolSize())
            .setMasterConnectionMinimumIdleSize(clusterServersConfig.getMasterConnectionMinimumIdleSize())
            .setMasterConnectionPoolSize(clusterServersConfig.getMasterConnectionPoolSize())
            .setSlaveConnectionMinimumIdleSize(clusterServersConfig.getSlaveConnectionMinimumIdleSize())
            .setSlaveConnectionPoolSize(clusterServersConfig.getSlaveConnectionPoolSize())
            .setReadMode(clusterServersConfig.getReadMode())
            .setSubscriptionMode(clusterServersConfig.getSubscriptionMode())
            .setSentinelAddresses(nodes);
        log.info("初始化 redis 配置: [mode={}, master={}, nodes={}]", redissonProperties.getMode(), redisProperties.getSentinel().getMaster(),
            nodes);
        return Redisson.create(config);
    }


    /**
     * 集群模式
     */
    @Primary
    @Bean(name = "redissonClient", destroyMethod = "shutdown")
    @ConditionalOnProperty(name = "redisson.mode", havingValue = RedissonProperties.MODE_CLUSTER)
    @ConditionalOnMissingBean(RedissonClient.class)
    RedissonClient cluster() {
        RedissonProperties.ClusterServersConfig clusterServersConfig = getClusterServersConfig();
        String finalPrefix = getRedisProtocolPrefix();
        List<String> nodes = redisProperties.getCluster().getNodes()
            .stream()
            .map(node -> finalPrefix + node)
            .collect(Collectors.toList());
        Config config = getConfig();
        // 集群方式
        config.useClusterServers()
            .setNameMapper(getNameMapper())
            .setConnectTimeout(((Long) redisProperties.getTimeout().toMillis()).intValue())
            .setPassword(StringUtils.isNotBlank(redisProperties.getPassword()) ? redisProperties.getPassword() : null)
            .setTimeout(clusterServersConfig.getTimeout())
            .setRetryAttempts(clusterServersConfig.getRetryAttempts())
            .setRetryInterval(clusterServersConfig.getRetryInterval())
            .setClientName(clusterServersConfig.getClientName())
            .setIdleConnectionTimeout(clusterServersConfig.getIdleConnectionTimeout())
            .setPingConnectionInterval(clusterServersConfig.getPingConnectionInterval())
            .setSubscriptionConnectionPoolSize(clusterServersConfig.getSubscriptionConnectionPoolSize())
            .setMasterConnectionMinimumIdleSize(clusterServersConfig.getMasterConnectionMinimumIdleSize())
            .setMasterConnectionPoolSize(clusterServersConfig.getMasterConnectionPoolSize())
            .setSlaveConnectionMinimumIdleSize(clusterServersConfig.getSlaveConnectionMinimumIdleSize())
            .setSlaveConnectionPoolSize(clusterServersConfig.getSlaveConnectionPoolSize())
            .setReadMode(clusterServersConfig.getReadMode())
            .setSubscriptionMode(clusterServersConfig.getSubscriptionMode())
            .setNodeAddresses(nodes);
        log.info("初始化 redis 配置: [mode={}, nodes={}]", redissonProperties.getMode(), nodes);
        return Redisson.create(config);
    }

    private String getRedisProtocolPrefix(){
        return redisProperties.isSsl() ? REDISS_PROTOCOL_PREFIX : REDIS_PROTOCOL_PREFIX;
    }

    private Config getConfig(){
        Config config = new Config();
        config.setThreads(redissonProperties.getThreads())
            .setNettyThreads(redissonProperties.getNettyThreads())
            .setCodec(JsonJacksonCodec.INSTANCE)
            .setTransportMode(redissonProperties.getTransportMode());
        return config;
    }

    private ClusterServersConfig getClusterServersConfig() {
        RedissonProperties.ClusterServersConfig clusterServersConfig = redissonProperties.getClusterServersConfig();
        if (ObjectUtil.isNull(clusterServersConfig)){
            throw new IllegalArgumentException("Redis connection 'redisson.clusterServersConfig' is empty");
        }
        return clusterServersConfig;
    }

    private NameMapper getNameMapper() {
        if (StringUtils.isBlank(redissonProperties.getKeyPrefix())){
            return new DefaultNameMapper();
        }
        return new KeyPrefixMapper(redissonProperties.getKeyPrefix());
    }


    /**
     * 整合spring-cache
     */
    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) {
        List<RedissonProperties.CacheGroup> cacheGroup = redissonProperties.getCacheGroup();
        Map<String, CacheConfig> config = new HashMap<>(cacheGroup.size());
        for (RedissonProperties.CacheGroup group : cacheGroup) {
            CacheConfig cacheConfig = new CacheConfig(group.getTtl(), group.getMaxIdleTime());
            cacheConfig.setMaxSize(group.getMaxSize());
            config.put(group.getGroupId(), cacheConfig);
        }
        return new RedissonSpringCacheManager(redissonClient, config, JsonJacksonCodec.INSTANCE);
    }

    /**
     * redis集群配置 yml
     *
     * --- # redis 集群配置(单机、集群、哨兵只能开启一个另两个需要注释掉)
     * spring:
     *   redis:
     *     sentinel:
     *       master: redis_master
     *       nodes:
     *         - 192.168.0.100:26379
     *         - 192.168.0.101:26379
     *         - 192.168.0.102:26379
     *     cluster:
     *       nodes:
     *         - 192.168.0.100:6379
     *         - 192.168.0.101:6379
     *         - 192.168.0.102:6379
     *     # 地址
     *     host: localhost
     *     # 端口，默认为6379
     *     port: 6379
     *     # 密码
     *     password: 12345678
     *     # 连接超时时间
     *     timeout: 10s
     *     # 是否开启ssl
     *     ssl: false
     *
     * redisson:
     *   # 线程池数量
     *   threads: 16
     *   # Netty线程池数量
     *   nettyThreads: 32
     *   # 传输模式
     *   transportMode: "NIO"
     *   # 缓存key前缀
     *   keyPrefix: ${ruoyi.name}
     *   # 配置方式
     *   mode: "SINGLE"
     *    --- # 当mode: "SINGLE" 配置 singleServerConfig节点，当mode: "CLUSTER/SENTINEL"时 配置clusterServersConfig节点
     *   # 单节点配置
     *   singleServerConfig:
     *     # 客户端名称
     *     clientName: ${ruoyi.name}
     *     # 最小空闲连接数
     *     connectionMinimumIdleSize: 8
     *     # 连接池大小
     *     connectionPoolSize: 32
     *     # 连接空闲超时，单位：毫秒
     *     idleConnectionTimeout: 10000
     *     # 命令等待超时，单位：毫秒
     *     timeout: 3000
     *     # 如果尝试在此限制之内发送成功，则开始启用 timeout 计时。
     *     retryAttempts: 3
     *     # 命令重试发送时间间隔，单位：毫秒
     *     retryInterval: 1500
     *     # 发布和订阅连接池大小
     *     subscriptionConnectionPoolSize: 50
     *   # 集群配置
     *   clusterServersConfig:
     *     # 客户端名称
     *     clientName: ${ruoyi.name}
     *     # master最小空闲连接数
     *     masterConnectionMinimumIdleSize: 32
     *     # master连接池大小
     *     masterConnectionPoolSize: 64
     *     # slave最小空闲连接数
     *     slaveConnectionMinimumIdleSize: 32
     *     # slave连接池大小
     *     slaveConnectionPoolSize: 64
     *     # 连接空闲超时，单位：毫秒
     *     idleConnectionTimeout: 10000
     *     # ping连接间隔
     *     pingConnectionInterval: 1000
     *     # 命令等待超时，单位：毫秒
     *     timeout: 3000
     *     # 如果尝试在此限制之内发送成功，则开始启用 timeout 计时。
     *     retryAttempts: 3
     *     # 命令重试发送时间间隔，单位：毫秒
     *     retryInterval: 1500
     *     # 发布和订阅连接池大小
     *     subscriptionConnectionPoolSize: 50
     *     # 读取模式
     *     readMode: "SLAVE"
     *     # 订阅模式
     *     subscriptionMode: "MASTER"
     */
}
