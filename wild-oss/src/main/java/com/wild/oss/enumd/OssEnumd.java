package com.wild.oss.enumd;

import com.wild.common.utils.StringUtils;
import com.wild.oss.service.impl.AliyunOssStrategy;
import com.wild.oss.service.impl.MinioOssStrategy;
import com.wild.oss.service.impl.QcloudOssStrategy;
import com.wild.oss.service.impl.QiniuOssStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 对象存储服务商枚举
 *
 * @author Lion Li
 */
@Getter
@AllArgsConstructor
public enum OssEnumd {

	/**
	 * 七牛云
	 */
	QINIU("qiniu", QiniuOssStrategy.class),

	/**
	 * 阿里云
	 */
	ALIYUN("aliyun", AliyunOssStrategy.class),

	/**
	 * 腾讯云
	 */
	QCLOUD("qcloud", QcloudOssStrategy.class),

	/**
	 * minio
	 */
	MINIO("minio", MinioOssStrategy.class);

	private final String value;

	private final Class<?> serviceClass;

	public static Class<?> getServiceClass(String value) {
		for (OssEnumd clazz : values()) {
			if (clazz.getValue().equals(value)) {
				return clazz.getServiceClass();
			}
		}
		return null;
	}

	public static String getServiceName(String value) {
		for (OssEnumd clazz : values()) {
			if (clazz.getValue().equals(value)) {
				return StringUtils.uncapitalize(clazz.getServiceClass().getSimpleName());
			}
		}
		return null;
	}


}
