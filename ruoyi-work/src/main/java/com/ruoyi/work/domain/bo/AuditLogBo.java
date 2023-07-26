package com.ruoyi.work.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 审核日志业务对象 audit_log
 *
 * @author ruoyi
 * @date 2023-03-22
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class AuditLogBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 审核员id
     */
    @NotNull(message = "审核员id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String auditId;

    /**
     * 审核员姓名
     */
    @NotBlank(message = "审核员姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String adminUserName;

    /**
     * 关联表id
     */
    @NotNull(message = "关联表id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String otherId;

    /**
     * 回复
     */
    @NotBlank(message = "回复不能为空", groups = { AddGroup.class, EditGroup.class })
    private String reply;

    /**
     * 状态 1.待提交 2.受理中 3.受理退件 4.受理驳回 5.初审中 6初审不通过 7.初审退件 8.审定中 9.审定不通过 10.审定通过
     */
    @NotBlank(message = "状态 1.待提交 2.受理中 3.受理退件 4.受理驳回 5.初审中 6初审不通过 7.初审退件 8.审定中 9.审定不通过 10.审定通过不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;

    /**
     * 部门角色
     */
    @NotBlank(message = "部门角色不能为空", groups = { AddGroup.class, EditGroup.class })
    private String roleName;

    /**
     * 推送记录
     */
    @NotBlank(message = "推送记录不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pushLog;

    /**
     * 审核员类型(1人员,2部门,3角色,4公司)
     */
    @NotBlank(message = "审核员类型(1人员,2部门,3角色,4公司)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String auditType;

    private String processKey;

    private String audit;

    private String step;


}
