package org.dromara.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 客户端状态
 *
 * @author lsh
 */
@Getter
@AllArgsConstructor
public enum ClientStatus {
    /**
     * 正常
     */
    OK("0", "正常"),
    /**
     * 停用
     */
    DISABLE("1", "停用"),
    /**
     * 删除
     */
    DELETED("2", "删除");

    private final String code;
    private final String info;
}
