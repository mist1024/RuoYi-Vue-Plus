package org.dromara.common.websocket.dto;

import lombok.Data;
import org.dromara.common.websocket.enums.MsgType;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 消息的dto
 *
 * @author zendwang
 */
@Data
public class WebSocketMessageDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 需要推送到的session key 列表
     */
    private List<Long> sessionKeys;

    /**
     * 消息类型
     */
    private MsgType msgType;

    /**
     * 需要发送的消息
     */
    private String message;
}
