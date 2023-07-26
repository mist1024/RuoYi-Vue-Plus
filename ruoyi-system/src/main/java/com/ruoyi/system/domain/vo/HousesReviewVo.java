package com.ruoyi.system.domain.vo;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.converters.date.DateDateConverter;
import com.alibaba.excel.converters.date.DateStringConverter;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import com.ruoyi.common.utils.MySerializerUtils;
import com.ruoyi.system.domain.BuyHousesReviewMember;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 购房复审登记视图对象 houses_review
 *
 * @author ruoyi
 * @date 2023-03-08
 */
@Data
@ExcelIgnoreUnannotated
public class HousesReviewVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
//    @ExcelProperty(value = "id")
//    @JsonSerialize(using = ToStringSerializer.class)
//    @JsonSerialize(using = MySerializerUtils.class)
    private Long id;

    private String step;

    @ExcelProperty(value = "序号")
    private String number;
    /**
     * 证件类型
     */
    @ExcelProperty(value = "证件类型")
    @NotNull(message = "证件类型不可为空")
    private String cardType;

    /**
     * 身份证号码
     */
    @ExcelProperty(value = "证件号码")
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
    @DateTimeFormat("yyyy-MM-dd")
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
//    @ExcelProperty(value = "状态")
    private String status;

    /**
     * 登记失效时间
     */
    @ExcelProperty(value = "登记失效时间")
    private String registerFailureTime;

    /**
     * 国籍
     */
    @ExcelProperty(value = "国籍", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "nationality_type")
    private String nationality;

    /**
     * 婚姻状况
     */
    @ExcelProperty(value = "婚姻状况",converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "marital_status")
    private String maritalStatus;

    /**
     * 单位类型
     */
    @ExcelProperty(value = "单位类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "company_type")
    private String companyType;

    /**
     * 公司名称
     */
    @ExcelProperty(value = "公司名称")
    private String companyName;

    /**
     * 人才类型
     */
    @ExcelProperty(value = "人才类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "talents_type")
    private String talentsType;

    /**
     * 统一社会信用代码
     */
//    @ExcelProperty(value = "统一社会信用代码")
    private String creditCode;

    /**
     * 企业所在地
     */
    @ExcelProperty(value = "企业所在地")
    private String companyAddress;

    /**
     * 来源
     */
    @ExcelProperty(value = "来源", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "source_by")
    private String sourceBy;

    /**
     * 流程key
     */

    private String processKey;

    private String processStatus;

    private String companyAddressArea;

    @TableField(exist = false)
    private List<BuyHousesReviewMember> buyHousesMemberList;

    private Date updateTime;

    /**
     * d类字段扩展
     */
    private String typeExtend;

    /**
     * 审核通过时间
     */
    @ExcelProperty(value = "审核通过时间",converter = DateStringConverter.class)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date passTime;

}


