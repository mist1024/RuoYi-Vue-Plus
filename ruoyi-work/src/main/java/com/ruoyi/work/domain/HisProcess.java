package com.ruoyi.work.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
     * 1(失败)，2（成功）
     */
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;


    @TableField(exist = false)
    private String processKey;

    @TableField(exist = false)
    private Map params;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    private String step;

    private String type;

    private String startUser;

    private String userId;

    @TableField(exist = false)
    private String roleId;

    @TableField(exist = false)
    private String deptId;

    @TableField(exist = false)
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

    private String description;

    @TableField(exist = false)
    private String audit1;

    private String updateTime;

    private Boolean isNext;

    /**
     * 该字段只对企业审核做扩展
     */
    @TableField(exist = false)
    private String companyId;

    /**
     * 证件号码
     */
    private  String cardId;

    /**
     * 特定标识
     */
    @TableField(exist = false)
    private String tiger;


    @TableField(exist = false)
    private String[] ids;

    /**
     * 项目名称
     */
    private String projectName;







}
