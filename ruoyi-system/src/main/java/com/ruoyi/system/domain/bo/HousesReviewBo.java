package com.ruoyi.system.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.system.domain.BuyHousesReviewMember;
import com.ruoyi.system.domain.MaterialModule;
import com.ruoyi.system.domain.MaterialProof;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 购房复审登记业务对象 houses_review
 *
 * @author ruoyi
 * @date 2023-03-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class HousesReviewBo extends BaseEntity {

    /**
     * id
     */
    @NotNull(message = "id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 证件类型
     */
    @NotBlank(message = "证件类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cardType;


    private String step;
    /**
     * 身份证号码
     */
    @NotBlank(message = "身份证号码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String card;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 资格序号
     */
    @NotBlank(message = "资格序号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String qualification;

    /**
     * 审核时间
     */
    @NotBlank(message = "审核时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private String auditTime;

    /**
     * 预售证号
     */
    @NotBlank(message = "预售证号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String presellCard;

    /**
     * 交易类型
     */
    @NotBlank(message = "交易类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String dealType;

    /**
     * 项目名称
     */
    @NotBlank(message = "项目名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String projectName;

    /**
     * 项目区域
     */
    @NotBlank(message = "项目区域不能为空", groups = { AddGroup.class, EditGroup.class })
    private String projectArea;

    /**
     * 资格确认时间
     */
    @NotBlank(message = "资格确认时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private String qualificationConfirmTime;

    /**
     * 资格预申请时间
     */
//    @NotBlank(message = "资格预申请时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private String qualificationPreApplyTime;

    /**
     * 家庭类型
     */
    @NotBlank(message = "家庭类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String familyType;

    /**
     * 状态
     */
//    @NotBlank(message = "状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;

    /**
     * 登记失效时间
     */
    @NotBlank(message = "登记失效时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private String registerFailureTime;

    /**
     * 国籍
     */
    @NotBlank(message = "国籍不能为空", groups = { AddGroup.class, EditGroup.class })
    private String nationality;

    /**
     * 婚姻状况
     */
    @NotBlank(message = "婚姻状况不能为空", groups = { AddGroup.class, EditGroup.class })
    private String maritalStatus;

    /**
     * 单位类型
     */
//    @NotBlank(message = "单位类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String companyType;

    /**
     * 公司名称
     */
    @NotBlank(message = "公司名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String companyName;

    /**
     * 人才类型
     */
    @NotBlank(message = "人才类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String talentsType;

    /**
     * 统一社会信用代码
     */
    @NotBlank(message = "统一社会信用代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String creditCode;

    /**
     * 企业所在地
     */
    @NotBlank(message = "企业所在地不能为空", groups = { AddGroup.class, EditGroup.class })
    private String companyAddress;

    /**
     * 来源
     */
    @NotBlank(message = "来源不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sourceBy;

    /**
     * 流程key
     */
    @NotBlank(message = "流程key不能为空", groups = { AddGroup.class, EditGroup.class })
    private String processKey;

    private String processStatus;

    private String companyAddressArea;

    private List<BuyHousesReviewMember> buyHousesMemberList;

    private List<MaterialModule> materialsList;

    private List<MaterialProof> materialProofList;

    /**
     * d类字段扩展
     */
    private String typeExtend;

    @TableField(exist = false)
    private Long[] ids;

    private Date passTime;

}
