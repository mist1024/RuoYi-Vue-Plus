package com.ruoyi.work.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * 历史运作对象 his_process
 *
 * @author ruoyi
 * @date 2023-02-03
 */
@Data
@TableName("his_process")
public class HisProcess{

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id")
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
     * 1fail(失败)，2success（成功）
     */
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    @TableField(exist = false)
    private String processKey;

    @TableField(exist = false)
    private Map params;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private String step;

    private String type;

    private String startUser;

    private String userId;

    private String roleId;

    private String deptId;

    private String companyUserId;

    /**
     * 相隔时间差
     */
    private String timeBetween;

    private String companyName;

    @TableField(exist = false)
    private String reply;

    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private String bean;



}
