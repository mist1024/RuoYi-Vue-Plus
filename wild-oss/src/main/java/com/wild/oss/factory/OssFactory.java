package com.wild.oss.factory;

import com.wild.common.utils.JsonUtils;
import com.wild.common.utils.RedisUtils;
import com.wild.common.utils.StringUtils;
import com.wild.common.utils.reflect.ReflectUtils;
import com.wild.oss.constant.OssConstant;
import com.wild.oss.enumd.OssEnumd;
import com.wild.oss.exception.OssException;
import com.wild.oss.properties.OssProperties;
import com.wild.oss.service.IOssStrategy;
import com.wild.oss.service.abstractd.AbstractOssStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文件上传Factory
 *
 * @author Lion Li
 */
@Slf4j
public class OssFactory {

	/**
	 * 服务实例缓存
	 */
	private static final Map<String, IOssStrategy> SERVICES = new ConcurrentHashMap<>();

    /**
     * 初始化工厂
     */
    public static void init() {
        log.info("初始化OSS工厂");
        RedisUtils.subscribe(OssConstant.CACHE_CONFIG_KEY, String.class, type -> {
            // 没有的实例不处理
            if (SERVICES.containsKey(type)) {
                refreshService(type);
                log.info("订阅刷新OSS配置 => " + type);
            }
        });
    }

	/**
	 * 获取默认实例
	 */
	public static IOssStrategy instance() {
		// 获取redis 默认类型
		String type = RedisUtils.getCacheObject(OssConstant.CACHE_CONFIG_KEY);
		if (StringUtils.isEmpty(type)) {
			throw new OssException("文件存储服务类型无法找到!");
		}
		return instance(type);
	}

	/**
	 * 根据类型获取实例
	 */
	public static IOssStrategy instance(String type) {
        IOssStrategy service = SERVICES.get(type);
		if (service == null) {
			refreshService(type);
			service = SERVICES.get(type);
		}
		return service;
	}

	private static void refreshService(String type) {
		Object json = RedisUtils.getCacheObject(OssConstant.SYS_OSS_KEY + type);
        OssProperties properties = JsonUtils.parseObject(json.toString(), OssProperties.class);
		if (properties == null) {
			throw new OssException("系统异常, '" + type + "'配置信息不存在!");
		}
		// 获取redis配置信息 创建对象 并缓存
        IOssStrategy service = (IOssStrategy) ReflectUtils.newInstance(OssEnumd.getServiceClass(type));
		((AbstractOssStrategy)service).init(properties);
		SERVICES.put(type, service);
	}

}
