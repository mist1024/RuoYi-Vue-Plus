package com.ruoyi.common.utils;

import cn.hutool.core.util.IdUtil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class IdUtils {

    public Long nextId() {
        return IdUtil.getSnowflake(1, 1).nextId();
    }
}
