package org.dromara.workflow.flowable.config;

import cn.hutool.core.collection.CollUtil;
import org.dromara.workflow.common.enums.TaskStatusEnum;
import org.dromara.workflow.flowable.strategy.FlowEventStrategy;
import org.dromara.workflow.flowable.strategy.FlowTaskEventHandler;
import org.flowable.bpmn.model.BoundaryEvent;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.common.engine.api.delegate.event.*;
import org.flowable.common.engine.impl.cfg.TransactionState;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.task.Comment;
import org.flowable.task.api.Task;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 引擎调度监听
 *
 * @author may
 */
@Component
public class GlobalFlowableListener implements FlowableEventListener {

    @Autowired
    @Lazy
    private TaskService taskService;

    @Autowired
    @Lazy
    private RuntimeService runtimeService;

    @Autowired
    @Lazy
    private RepositoryService repositoryService;

    @Autowired
    private FlowEventStrategy flowEventStrategy;

    @Override
    public void onEvent(FlowableEvent flowableEvent) {
        if (flowableEvent instanceof FlowableEngineEvent flowableEngineEvent) {
            FlowableEngineEventType engineEventType = (FlowableEngineEventType) flowableEvent.getType();
            switch (engineEventType) {
                case JOB_EXECUTION_SUCCESS -> jobExecutionSuccess((FlowableEngineEntityEvent) flowableEngineEvent);
                case TASK_COMPLETED -> taskCompleted((FlowableEngineEntityEvent) flowableEngineEvent);
            }
        }
    }

    @Override
    public boolean isFailOnException() {
        return true;
    }

    @Override
    public boolean isFireOnTransactionLifecycleEvent() {
        return false;
    }

    @Override
    public String getOnTransaction() {
        return TransactionState.COMMITTED.name();
    }

    /**
     * 处理边界定时事件自动审批记录
     *
     * @param event 事件
     */
    protected void jobExecutionSuccess(FlowableEngineEntityEvent event) {
        Execution execution = runtimeService.createExecutionQuery().executionId(event.getExecutionId()).singleResult();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(event.getProcessDefinitionId());
        FlowElement flowElement = bpmnModel.getFlowElement(execution.getActivityId());
        if (flowElement instanceof BoundaryEvent) {
            String attachedToRefId = ((BoundaryEvent) flowElement).getAttachedToRefId();
            List<Execution> list = runtimeService.createExecutionQuery().activityId(attachedToRefId).list();
            for (Execution ex : list) {
                Task task = taskService.createTaskQuery().executionId(ex.getId()).singleResult();
                if (task != null) {
                    List<Comment> taskComments = taskService.getTaskComments(task.getId());
                    if (CollUtil.isEmpty(taskComments)) {
                        taskService.addComment(task.getId(), task.getProcessInstanceId(), TaskStatusEnum.PASS.getStatus(), "自动审批！");
                    }
                }
            }
        }
    }

    /**
     * 处理任务办理监听
     *
     * @param event 事件
     */
    protected void taskCompleted(FlowableEngineEntityEvent event) {
        TaskEntity entity = (TaskEntity) event.getEntity();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(entity.getProcessDefinitionId()).singleResult();
        String processDefinitionKey = processDefinition.getKey();
        String taskDefinitionKey = entity.getTaskDefinitionKey();
        String key = processDefinitionKey + "_" + taskDefinitionKey;
        FlowTaskEventHandler handler = flowEventStrategy.getTaskHandler(key);
        if (handler != null) {
            handler.handleTask(entity);
        }
    }
}
