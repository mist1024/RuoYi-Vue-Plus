package org.dromara.common.liteflow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 规则引擎事件枚举
 *
 * @author AprilWind
 */
@Getter
@AllArgsConstructor
public enum LiteflowType {

    /**
     * 重新加载规则
     */
    RELOAD_CHAIN("reload_chain"),

    /**
     * 移除规则
     */
    REMOVE_CHAIN("remove_chain"),

    /**
     * 重新加载脚本
     */
    RELOAD_SCRIPT("reload_script"),

    /**
     * 卸载脚本节点
     */
    UNLOAD_SCRIPT("unload_script");

    private final String flowBus;
}
