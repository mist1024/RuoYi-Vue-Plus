package com.ruoyi.work.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * 流程运作对象 act_process
 *
 * @author ruoyi
 * @date 2023-02-03
 */
@Data
@TableName("act_process")
public class ActProcess {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 流程表id
     */
    private String processId;
    /**
     * 业务id
     */
    private String businessId;
    /**
     * 审核
     */
    private String audit;
    /**
     * 审核类型（1，人员，2.部门，3角色,4公司）
     */
    private String checkType;
    /**
     * 步骤
     */
    private String step;

    private Date createTime;

    /**
     * 是由存在下一步骤
     */
    private Boolean isNext;

    private String type;

    private String startUser;

    private String userId;

    @TableField(exist = false)
    private String roleId;

    @TableField(exist = false)
    private String deptId;

    @TableField(exist = false)
    private String name;

    private String description;

    @TableField(exist = false)
    private String bean;

    @TableField(exist = false)
    private String companyUserId;

    private String companyName;

    @TableField(exist = false)
    private String timeout;

    @TableField(exist = false)
    private String processKey;

    /**
     * 证件号码
     */
    private  String cardId;

    @TableField(exist = false)
    private String apiKey;

    /**
     * 特定标识
     */
    @TableField(exist = false)
    private String tiger;

    @TableField(exist = false)
    private String[] stepList;

    /**
     * 项目名称
     */
    private String projectName;


}
