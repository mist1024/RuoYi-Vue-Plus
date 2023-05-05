package com.ruoyi.work.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 流程对象 process
 *
 * @author ruoyi
 * @date 2023-02-03
 */
@Data
@TableName("process")
public class TProcess {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 流程名称
     */
    private String name;
    /**
     * 流程key
     */
    private String processKey;
    /**
     * 流程步骤
     */
    private String step;
    /**
     * 流程类型(1,普通类型，2会签，3根据接口获取审核部门)
     */
    private String type;
    /**
     * 审核类型（1，人员，2.部门，3角色）
     */
    private String checkType;
    /**
     * 审核人员
     */
    private Long processCheck;
    /**
     * 抄送人员
     */
    private String cc;
    /**
     * 是由存在下一步骤
     */
    private Boolean isNext;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 业务规则接口id
     */
    private Long ruleId;

    /**
     * 如果不是根据接口获取人员此处就是人员id获取部门,角色id,否则就是空的
     */
    private String audit;

    private String param;

    private String timeout;

    private String paramName;

    private String bean;

    private String description;

}
