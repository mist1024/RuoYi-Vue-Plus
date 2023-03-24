package com.ruoyi.work.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 审核日志视图对象 audit_log
 *
 * @author ruoyi
 * @date 2023-03-22
 */
@Data
@ExcelIgnoreUnannotated
public class AuditLogVo {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 审核员id
     */
    @ExcelProperty(value = "审核员id")
    private String auditId;

    /**
     * 审核员姓名
     */
    @ExcelProperty(value = "审核员姓名")
    private String adminUserName;

    /**
     * 关联表id
     */
    @ExcelProperty(value = "关联表id")
    private String otherId;

    /**
     * 回复
     */
    @ExcelProperty(value = "回复")
    private String reply;

    /**
     * 状态 1.待提交 2.受理中 3.受理退件 4.受理驳回 5.初审中 6初审不通过 7.初审退件 8.审定中 9.审定不通过 10.审定通过
     */
    @ExcelProperty(value = "状态 1.待提交 2.受理中 3.受理退件 4.受理驳回 5.初审中 6初审不通过 7.初审退件 8.审定中 9.审定不通过 10.审定通过")
    private String status;

    /**
     * 部门角色
     */
    @ExcelProperty(value = "部门角色")
    private String roleName;

    /**
     * 推送记录
     */
    @ExcelProperty(value = "推送记录")
    private String pushLog;

    /**
     * 审核员类型(1人员,2部门,3角色,4公司)
     */
    @ExcelProperty(value = "审核员类型(1人员,2部门,3角色,4公司)")
    private String auditType;

    private String processKey;

    private String audit;

    private String step;



}
