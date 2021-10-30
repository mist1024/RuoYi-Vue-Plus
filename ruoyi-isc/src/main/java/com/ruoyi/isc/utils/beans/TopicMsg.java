package com.ruoyi.isc.utils.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Wenchao Gong
 * @date 2021-10-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicMsg implements Serializable {
    /** 消息ID */
    private String id;

    /** 消息内容 */
    private String msg;

    /** 消息类型 */
    private Type type;

    /** 消息类型枚举 */
    public static enum Type {
        /** 新增单条记录 */
        ADD,
        /** 更新单条记录 */
        UPDATE,
        /** 删除单条记录 */
        DELETE,
        /** 刷新所有 */
        REFRESH
    }
}
