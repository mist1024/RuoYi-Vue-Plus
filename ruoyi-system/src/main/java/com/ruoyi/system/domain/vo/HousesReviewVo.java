package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.system.domain.BuyHousesReviewMember;
import lombok.Data;

import java.util.List;


/**
 * 购房复审登记视图对象 houses_review
 *
 * @author ruoyi
 * @date 2023-03-08
 */
@Data
@ExcelIgnoreUnannotated
public class HousesReviewVo {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ExcelProperty(value = "id")
    private Long id;

    /**
     * 证件类型
     */
    @ExcelProperty(value = "证件类型")
    private String cardType;

    /**
     * 身份证号码
     */
    @ExcelProperty(value = "身份证号码")
    private String card;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名")
    private String name;

    /**
     * 资格序号
     */
    @ExcelProperty(value = "资格序号")
    private String qualification;

    /**
     * 审核时间
     */
    @ExcelProperty(value = "审核时间")
    private String auditTime;

    /**
     * 预售证号
     */
    @ExcelProperty(value = "预售证号")
    private String presellCard;

    /**
     * 交易类型
     */
    @ExcelProperty(value = "交易类型")
    private String dealType;

    /**
     * 项目名称
     */
    @ExcelProperty(value = "项目名称")
    private String projectName;

    /**
     * 项目区域
     */
    @ExcelProperty(value = "项目区域")
    private String projectArea;

    /**
     * 资格确认时间
     */
    @ExcelProperty(value = "资格确认时间")
    private String qualificationConfirmTime;

    /**
     * 资格预申请时间
     */
    @ExcelProperty(value = "资格预申请时间")
    private String qualificationPreApplyTime;

    /**
     * 家庭类型
     */
    @ExcelProperty(value = "家庭类型")
    private String familyType;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态")
    private String status;

    /**
     * 登记失效时间
     */
    @ExcelProperty(value = "登记失效时间")
    private String registerFailureTime;

    /**
     * 国籍
     */
//    @ExcelProperty(value = "国籍")
    private String nationality;

    /**
     * 婚姻状况
     */
//    @ExcelProperty(value = "婚姻状况")
    private String maritalStatus;

    /**
     * 单位类型
     */
//    @ExcelProperty(value = "单位类型")
    private String companyType;

    /**
     * 公司名称
     */
//    @ExcelProperty(value = "公司名称")
    private String companyName;

    /**
     * 人才类型
     */
//    @ExcelProperty(value = "人才类型")
    private String talentsType;

    /**
     * 统一社会信用代码
     */
//    @ExcelProperty(value = "统一社会信用代码")
    private String creditCode;

    /**
     * 企业所在地
     */
//    @ExcelProperty(value = "企业所在地")
    private String companyAddress;

    /**
     * 来源
     */
//    @ExcelProperty(value = "来源")
    private String sourceBy;

    /**
     * 流程key
     */

    private String processKey;

    private String processStatus;

    private String companyAddressArea;

    private List<BuyHousesReviewMember> buyHousesMemberList;


}


