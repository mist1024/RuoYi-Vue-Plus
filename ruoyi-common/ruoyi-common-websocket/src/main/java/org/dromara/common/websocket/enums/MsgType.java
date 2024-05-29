package org.dromara.common.websocket.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息结构类型
 *
 * @author AprilWind
 */
@Getter
@AllArgsConstructor
public enum MsgType {

    /**
     * 消息
     */
    MSG("msg"),

    /**
     * 通知
     */
    NOTIFY("notify"),

    /**
     * 待办
     */
    TODO("todo");

    private final String msgType;

}
