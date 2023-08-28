package com.ruoyi.work.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 流程运作业务对象 act_process
 *
 * @author ruoyi
 * @date 2023-02-03
 */

@Data
public class ActProcessBo {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 流程表id
     */
    @NotBlank(message = "流程表id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String processId;

    /**
     * 业务id
     */
    @NotBlank(message = "业务id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String businessId;

    /**
     * 审核
     */
    @NotBlank(message = "审核不能为空", groups = { AddGroup.class, EditGroup.class })
    private String audit;

    /**
     * 审核类型（1，人员，2.部门，3角色）
     */
    @NotBlank(message = "审核类型（1，人员，2.部门，3角色）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String checkType;

    /**
     * 步骤
     */
    @NotBlank(message = "步骤不能为空", groups = { AddGroup.class, EditGroup.class })
    private String step;

    private String userId;

    private String description;

    /**
     * 证件号码
     */
    private  String cardId;

    /**
     * 项目名称
     */
    private String projectName;


}
