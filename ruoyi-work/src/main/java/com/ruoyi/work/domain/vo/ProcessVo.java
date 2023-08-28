package com.ruoyi.work.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import com.ruoyi.work.domain.AuditLog;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 流程视图对象 process
 *
 * @author ruoyi
 * @date 2023-02-03
 */
@Data
@ExcelIgnoreUnannotated
public class ProcessVo {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 流程名称
     */
    @ExcelProperty(value = "流程名称")
    private String name;

    /**
     * 流程key
     */
    @ExcelProperty(value = "流程key")
    private String processKey;

    /**
     * 流程步骤
     */
    @ExcelProperty(value = "流程步骤")
    private String step;

    /**
     * 流程类型(1,普通类型，2会签，3根据接口获取审核部门)
     */
    @ExcelProperty(value = "流程类型(1,普通类型，2会签，3根据接口获取审核部门)")
    private String type;

    /**
     * 审核类型（1，人员，2.部门，3角色）
     */
    @ExcelProperty(value = "审核类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "1=，人员，2.部门，3角色")
    private String checkType;

    /**
     * 审核人员
     */
    @ExcelProperty(value = "审核人员类型(1普通,2根据接口获取人员)")
    private Long processCheck;

    /**
     * 抄送人员
     */
    @ExcelProperty(value = "抄送人员")
    private String cc;

    /**
     * 是由存在下一步骤
     */
    @ExcelProperty(value = "是由存在下一步骤")
    private Boolean isNext;

    /**
     * 如果不是根据接口获取人员此处就是人员id获取部门,角色id,否则就是空的
     */
    private String audit;

    /**
     * 接口所需参数
     */
    private String param;

    private String paramName;

    private Map params;

    private Long ruleId;

    private String businessId;

    private String startUser;

    private String timeout;

    @TableField(exist = false)
    private String checked;

    @TableField(exist = false)
    private String audit1;

    private String bean;

    private String description;

    @TableField(exist = false)
    private List<AuditLog> auditLogList;

    @TableField(exist = false)
    private List<AuditLog> auditLogList1;

    @TableField(exist = false)
    private Integer size;

    @TableField(exist = false)
    private List<ActProcessVo> actProcessVoList;

    private Date createTime;

    private String companyName;

    private String cardId;

    @TableField(exist = false)
    private String userId;

    @TableField(exist = false)
    private String projectName;




}
