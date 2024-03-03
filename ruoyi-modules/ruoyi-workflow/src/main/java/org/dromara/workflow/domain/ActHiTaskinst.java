package org.dromara.workflow.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

import java.io.Serial;

/**
 * 流程历史任务对象 act_hi_taskinst
 *
 * @author gssong
 * @date 2024-03-02
 */
@Data
@TableName("act_hi_taskinst")
public class ActHiTaskinst {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "id_")
    private String id;

    /**
     * 版本
     */
    @TableField(value = "rev_")
    private Long rev;

    /**
     * 流程定义id
     */
    @TableField(value = "proc_def_id_")
    private String procDefId;

    /**
     *
     */
    @TableField(value = "task_def_id_")
    private String taskDefId;

    /**
     * 任务节点id
     */
    @TableField(value = "task_def_key_")
    private String taskDefKey;

    /**
     * 流程实例id
     */
    @TableField(value = "proc_inst_id_")
    private String procInstId;

    /**
     * 流程执行id
     */
    @TableField(value = "execution_id")
    private String executionId;

    /**
     *
     */
    @TableField(value = "scope_id_")
    private String scopeId;

    /**
     *
     */
    @TableField(value = "sub_scope_id_")
    private String subScopeId;

    /**
     * 先用当前字段标识抄送类型
     */
    @TableField(value = "scope_type_")
    private String scopeType;

    /**
     *
     */
    @TableField(value = "scope_definition_id_")
    private String scopeDefinitionId;

    /**
     *
     */
    @TableField(value = "propagated_stage_inst_id_")
    private String propagatedStageInstId;

    /**
     * 任务名称
     */
    @TableField(value = "name_")
    private String name;

    /**
     * 父级id
     */
    @TableField(value = "parent_task_id_")
    private String parentTaskId;

    /**
     * 描述
     */
    @TableField(value = "description_")
    private String description;

    /**
     * 办理人
     */
    @TableField(value = "owner_")
    private String owner;

    /**
     * 办理人
     */
    @TableField(value = "assignee_")
    private String assignee;

    /**
     * 开始事件
     */
    @TableField(value = "start_time_")
    private Date startTime;

    /**
     * 认领时间
     */
    @TableField(value = "claim_time_")
    private Date claimTime;

    /**
     * 结束时间
     */
    @TableField(value = "end_time_")
    private Date endTime;

    /**
     * 持续时间
     */
    @TableField(value = "duration_")
    private Long duration;

    /**
     * 删除原因
     */
    @TableField(value = "delete_reason_")
    private String deleteReason;

    /**
     * 优先级
     */
    @TableField(value = "priority_")
    private Long priority;

    /**
     * 到期时间
     */
    @TableField(value = "due_date_")
    private Date dueDate;

    /**
     *
     */
    @TableField(value = "form_key_")
    private String formKey;

    /**
     * 分类
     */
    @TableField(value = "category_")
    private String category;

    /**
     * 最后修改时间
     */
    @TableField(value = "last_updated_time_")
    private Date lastUpdatedTime;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id_")
    private String tenantId;


}
