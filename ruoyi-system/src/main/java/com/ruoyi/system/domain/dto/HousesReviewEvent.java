package com.ruoyi.system.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.system.domain.BuyHousesMember;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 购房复审登记对象 houses_review
 *
 * @author ruoyi
 * @date 2023-03-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HousesReviewEvent extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 证件类型
     */
    private String cardType;
    /**
     * 身份证号码
     */
    private String card;
    /**
     * 姓名
     */
    private String name;
    /**
     * 资格序号
     */
    private String qualification;
    /**
     * 审核时间
     */
    private String auditTime;
    /**
     * 预售证号
     */
    private String presellCard;
    /**
     * 交易类型
     */
    private String dealType;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 项目区域
     */
    private String projectArea;
    /**
     * 资格确认时间
     */
    private String qualificationConfirmTime;
    /**
     * 资格预申请时间
     */
    private String qualificationPreApplyTime;
    /**
     * 家庭类型
     */
    private String familyType;
    /**
     * 状态
     */
    private String status;
    /**
     * 登记失效时间
     */
    private String registerFailureTime;
    /**
     * 国籍
     */
    private String nationality;
    /**
     * 婚姻状况
     */
    private String maritalStatus;
    /**
     * 单位类型
     */
    private String companyType;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 人才类型
     */
    private String talentsType;
    /**
     * 统一社会信用代码
     */
    private String creditCode;
    /**
     * 企业所在地
     */
    private String companyAddress;
    /**
     * 来源
     */
    private String sourceBy;
    /**
     * 流程key
     */
    private String processKey;

    /**
     * 流程状态
     */
    private String processStatus;

    /**
     * 工作单位所属区域
     */
    private String companyAddressArea;

    /**
     * d类字段扩展
     */
    private String typeExtend;

    private Date passTime;

    @TableField(exist = false)
    private Long excelId;


    @TableField(exist = false)
    private String description;

    @TableField(exist = false)
    private Long[] ids;

}
