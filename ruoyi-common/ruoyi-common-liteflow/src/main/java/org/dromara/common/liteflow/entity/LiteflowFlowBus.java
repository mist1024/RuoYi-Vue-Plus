package org.dromara.common.liteflow.entity;

import lombok.Data;
import org.dromara.common.liteflow.enums.LiteflowType;

import java.io.Serial;
import java.io.Serializable;

/**
 * 规则引擎事件
 *
 * @author AprilWind
 */
@Data
public class LiteflowFlowBus implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 规则引擎事件类型
     */
    private LiteflowType liteflowType;

    /**
     * 事件关联的ID
     */
    private String id;

    /**
     * 事件的内容
     */
    private String content;

}
