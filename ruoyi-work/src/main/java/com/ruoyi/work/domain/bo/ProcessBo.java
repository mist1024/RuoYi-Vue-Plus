package com.ruoyi.work.domain.bo;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 流程业务对象 process
 *
 * @author ruoyi
 * @date 2023-02-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ProcessBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 流程名称
     */
    @NotBlank(message = "流程名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 流程key
     */
    @NotBlank(message = "流程key不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ProcessKey;

    /**
     * 流程步骤
     */
    @NotBlank(message = "流程步骤不能为空", groups = { AddGroup.class, EditGroup.class })
    private String step;

    /**
     * 流程类型(1,普通类型，2会签，3根据接口获取审核部门)
     */
    @NotBlank(message = "流程类型(1,普通类型，2会签，3根据接口获取审核部门)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String type;

    /**
     * 审核类型（1，人员，2.部门，3角色）
     */
    @NotBlank(message = "审核类型（1，人员，2.部门，3角色）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String checkType;

    /**
     * 审核人员
     */
    @NotBlank(message = "审核人员不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long processCheck;

    /**
     * 抄送人员
     */
    @NotBlank(message = "抄送人员不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cc;

    /**
     * 是由存在下一步骤
     */
    @NotNull(message = "是由存在下一步骤不能为空", groups = { AddGroup.class, EditGroup.class })
    private Boolean isNext;

    /**
     * 业务规则接口id
     */
    private Long ruleId;

    /**
     * 如果不是根据接口获取人员此处就是人员id获取部门,角色id,否则就是空的
     */
    private String audit;

    private String timeout;

    private String paramName;

    private String bean;

    private String description;


}
