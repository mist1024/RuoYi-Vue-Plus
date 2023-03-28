package com.ruoyi.common.websocket.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 消息的dto
 *
 * @author zendwang
 */
@Data
@Accessors(chain = true)
public class WebSocketMessageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 需要推送到的session key 列表
     */
    private List<Long> sessionKeys;

    /**
     * 需要发送的消息
     */
    private String message;

    public static WebSocketMessageDto build(List<Long> sessionKeys, String message) {
        return new WebSocketMessageDto().setMessage(message).setSessionKeys(sessionKeys);
    }
}
