package com.ruoyi.framework.config.redisson;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.NameMapper;

/**
 * redisson key 自动加前缀 解决多应用共用一个redis环境时key覆盖的问题
 *
 * @author im_libin@yeah.net
 * @date 2022/3/21 2:50 下午
 */
public class KeyPrefixMapper implements NameMapper {

    private final String keyPrefix;

    public KeyPrefixMapper(String keyPrefix) {
        this.keyPrefix = keyPrefix + ":";
    }

    @Override
    public String map(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        if (!name.startsWith(keyPrefix)) {
            return keyPrefix + name;
        } else {
            return name;
        }
    }

    @Override
    public String unmap(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        if (name.startsWith(keyPrefix)) {
            return name.substring(keyPrefix.length());
        }
        return name;
    }
}
