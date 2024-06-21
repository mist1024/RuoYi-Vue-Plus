package org.dromara.liteflow.listener;

import com.yomahub.liteflow.flow.FlowBus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.liteflow.entity.LiteflowFlowBus;
import org.dromara.common.liteflow.enums.LiteflowType;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 规则引擎 侦听器
 *
 * @author AprilWind
 * @date 2024-06-21
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class LiteflowListener {

    /**
     * 处理规则引擎事件
     *
     * @param liteflowFlowBus 包含规则引擎事件信息的对象
     */
    @Async
    @EventListener
    public void handleLiteflowEvent(LiteflowFlowBus liteflowFlowBus) {
        // 获取事件的类型
        LiteflowType flowBusType = liteflowFlowBus.getLiteflowType();
        // 获取事件关联的ID
        String id = liteflowFlowBus.getId();
        // 获取事件的内容
        String content = liteflowFlowBus.getContent();

        // 根据事件类型执行相应的操作
        switch (flowBusType) {
            case RELOAD_CHAIN:
                // 重新加载链式规则
                FlowBus.reloadChain(id, content);
                log.info("重新加载链式规则:{}", id);
                break;
            case REMOVE_CHAIN:
                // 移除链式规则
                FlowBus.removeChain(id);
                log.info("移除链式规则:{}", id);
                break;
            case RELOAD_SCRIPT:
                // 重新加载脚本
                FlowBus.reloadScript(id, content);
                log.info("重新加载脚本:{}", id);
                break;
            case UNLOAD_SCRIPT:
                // 卸载脚本节点
                FlowBus.unloadScriptNode(id);
                log.info("卸载脚本节点:{}", id);
                break;
            default:
                log.error("未知的LiteflowType: {}", flowBusType);
        }
    }

    /**
     * 异步处理规则引擎事件列表
     * 注意：虽然方法标记了 @Async 注解，但内部的方法调用不会异步执行，需要手动使用 TaskExecutor 实现异步
     *
     * @param liteflowFlowBus 包含规则引擎事件信息的对象列表
     */
    @Async
    @EventListener
    public void handleLiteflowEvent(List<LiteflowFlowBus> liteflowFlowBus) {
        liteflowFlowBus.forEach(this::handleLiteflowEvent);
    }

}
