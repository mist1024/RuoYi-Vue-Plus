package com.ruoyi.work.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 审核日志对象 audit_log
 *
 * @author ruoyi
 * @date 2023-03-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("audit_log")
public class AuditLog extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 审核员id
     */
    private String auditId;
    /**
     * 审核员姓名
     */
    private String adminUserName;
    /**
     * 关联表id
     */
    private String otherId;
    /**
     * 回复
     */
    private String reply;
    /**
     * 状态 1.待提交 2.受理中 3.受理退件 4.受理驳回 5.初审中 6初审不通过 7.初审退件 8.审定中 9.审定不通过 10.审定通过
     */
    private String status;
    /**
     * 部门角色
     */
    private String roleName;
    /**
     * 推送记录
     */
    private String pushLog;
    /**
     * 审核员类型(1人员,2部门,3角色,4公司)
     */
    private String auditType;

    private String processKey;

    private String audit;

    private String step;

}
