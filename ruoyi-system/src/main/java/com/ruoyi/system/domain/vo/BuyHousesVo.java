package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.converters.date.DateStringConverter;
import com.alibaba.excel.converters.url.UrlImageConverter;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import com.ruoyi.system.domain.BuyHousesMember;
import com.ruoyi.system.domain.MaterialProof;
import lombok.Data;

import java.net.URL;
import java.util.Date;
import java.util.List;


/**
 * 【请填写功能名称】视图对象 buy_houses
 *
 * @author ruoyi
 * @date 2023-02-24
 */
@Data
@ExcelIgnoreUnannotated
public class BuyHousesVo {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
//    @ExcelProperty(value = "")
    private Long id;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名")
    private String userName;

    /**
     * 户口簿内页/护照内页
     */
//    @ExcelProperty(value = "户口簿内页/护照内页")
    private String insidepageUrl;

    /**
     * 身份证/护照
     */
    @ExcelProperty(value = "证件号码")
    private String cardId;

    /**
     * 承诺书
     */
//    @ExcelProperty(value = "承诺书")
    private String commitmentUrl;

    /**
     * 单位地址
     */
    @ExcelProperty(value = "单位地址")
    private String companyAddress;

    /**
     * 工作单位
     */
    @ExcelProperty(value = "工作单位")
    private String companyName;



    /**
     * 申明书
     */
//    @ExcelProperty(value = "申明书")
    private String declarationUrl;

    /**
     * 企业所在地 1.新经济活力区 2.交子公园金融商务区 3.高新西区 4.生物城
     */
    @ExcelProperty(value = "企业所在地",converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "company_address_area")
    private String district;

    /**
     * 学历
     */
    @ExcelProperty(value = "学历")
    private String education;

    /**
     * 身份证正面
     */
//    @ExcelProperty(value = "身份证正面")
    private String frontUrl;

    /**
     * 公园城市局审核状态 1.待审核 2.退件 3.不通过 4.通过
     */
//    @ExcelProperty(value = "公园城市局审核状态 1.待审核 2.退件 3.不通过 4.通过")
    private String gyStatus;

    /**
     * 房屋记录
     */
//    @ExcelProperty(value = "房屋记录")
    private String homeRecordUrl;

    /**
     * 户口簿主页
     */
//    @ExcelProperty(value = "户口簿主页")
    private String homepageUrl;

    /**
     * 劳动合同
     */
//    @ExcelProperty(value = "劳动合同")
    private String laborContractUrl;

    /**
     * 企业营业执照
     */
//    @ExcelProperty(value = "企业营业执照")
    private String licenseUrl;

    /**
     * 婚姻状况 1.未婚 2.已婚 3.离异
     */
    @ExcelProperty(value = "婚姻状况",converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "marital_status")
    private String maritalStatus;

    /**
     * 婚姻证明材料
     */
//    @ExcelProperty(value = "婚姻证明材料")
    private String maritalUrl;

    /**
     * 国籍
     */
    @ExcelProperty(value = "国籍")
    private String nationality;

    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号")
    private String phone;

    /**
     * 区域部门审核状态 1.待审核 2.退件 3.不通过 4.通过
     */
//    @ExcelProperty(value = "区域部门审核状态 1.待审核 2.退件 3.不通过 4.通过")
    private String qyStatus;

    /**
     * 身份证背面
     */
//    @ExcelProperty(value = "身份证背面")
    private String reverseUrl;

    /**
     * 性别
     */
    @ExcelProperty(value = "性别")
    private String sex;

    /**
     * 社会事业局审核状态 1.待审核 2.退件 3.不通过 4.通过
     */
//    @ExcelProperty(value = "社会事业局审核状态 1.待审核 2.退件 3.不通过 4.通过")
    private String shStatus;

    /**
     * 统一社会信用代码
     */
    @ExcelProperty(value = "统一社会信用代码")
    private String socialCode;

    /**
     * 社保证明
     */
//    @ExcelProperty(value = "社保证明")
    private String socialSecurityUrl;

    /**
     * 状态 1.待提交 2.受理中 3.受理退件 4.受理驳回 5.初审中 6初审不通过 7.初审退件 8.审定中 9.审定不通过 10.审定通过
     */
//    @ExcelProperty(value = "状态 1.待提交 2.受理中 3.受理退件 4.受理驳回 5.初审中 6初审不通过 7.初审退件 8.审定中 9.审定不通过 10.审定通过")
    private String status;

    /**
     * 类型 A B C D
     */
    @ExcelProperty(value = "类型")
    private String type;

    /**
     * 用户id
     */
//    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     *
     */
    @ExcelProperty(value = "创建时间",converter = DateStringConverter.class)
    private Date createTime;

    @ExcelProperty(value = "修改时间",converter = DateStringConverter.class)
    private Date updateTime;

    /**
     *
     */
    @ExcelProperty(value = "审核通过时间",converter = DateStringConverter.class)
    private Date passTime;

    /**
     *
     */
//    @ExcelProperty(value = "人才影像卡")
    private String pictureInformationUrl;

    /**
     *
     */
//    @ExcelProperty(value = "")
    private String workAddress;

    private String processKey;

    private String processStatus;

    @TableField(exist = false)
    private List<BuyHousesMember> buyHousesMemberList;

    private String version;

    @TableField(exist = false)
    private List<MaterialProof> materialProofList;


    private String companyId;

    private String apiKey;

    private String step;

}
