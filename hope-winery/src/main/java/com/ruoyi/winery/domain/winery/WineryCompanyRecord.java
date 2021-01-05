package com.ruoyi.winery.domain.winery;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.winery.enums.IrrigationTypeEnum;
import com.ruoyi.winery.enums.SoilTypeEnum;
import com.ruoyi.winery.enums.WineryStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.ruoyi.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 酒庄厂家登记记录对象 winery_company_record
 * 
 * @author ruoyi
 * @date 2020-12-18
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("winery_company_record")
public class WineryCompanyRecord implements Serializable {

private static final long serialVersionUID=1L;


    /** id */
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 创建者openid */
    @Excel(name = "创建者openid")
    private String openid;

    /** 联系邮箱 */
    @Excel(name = "联系邮箱")
    private String email;

    /** 联系人姓名 */
    @Excel(name = "联系人姓名")
    private String personName;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String mobile;

    /** 酒庄名称 */
    @Excel(name = "酒庄名称")
    private String wineryName;

    /** 建立时间 */
    @Excel(name = "建立时间")
    private String buildTime;

    /** 酒庄地址（区） */
    @Excel(name = "酒庄地址" , readConverterExp = "区=")
    private String region;

    /** 酒庄地址（具体） */
    @Excel(name = "酒庄地址" , readConverterExp = "具=体")
    private String address;

    /** 总面积 */
    @Excel(name = "总面积")
    private Long wineryArea;

    /** 建筑面积 */
    @Excel(name = "建筑面积")
    private Long buildArea;

    /** 酒庄现状 */
    @Excel(name = "酒庄现状")
    private WineryStatusEnum wineryStatus;

    /** 面积（亩） */
    @Excel(name = "面积" , readConverterExp = "亩=")
    private Long plantArea;

    /** 产量（斤） */
    @Excel(name = "产量" , readConverterExp = "斤=")
    private Long plantWeight;

    /** 土壤类型 */
    @Excel(name = "土壤类型")
    private SoilTypeEnum soilType;

    /** 红葡萄品种 */
    @Excel(name = "红葡萄品种")
    private String redVariety;

    /** 白葡萄品种 */
    @Excel(name = "白葡萄品种")
    private String whiteVariety;

    /** 灌溉方式 */
    @Excel(name = "灌溉方式")
    private IrrigationTypeEnum irrigationType;

    /** 发酵工艺 */
    @Excel(name = "发酵工艺")
    private String fermentationProcess;

    /** 热化容器 */
    @Excel(name = "热化容器")
    private String container;

    /** 澄清方式 */
    @Excel(name = "澄清方式")
    private String clarificationMethod;

    /** 年产量 */
    @Excel(name = "年产量")
    private Float annualOutput;

    /** 库存 */
    @Excel(name = "库存")
    private Float stock;

    /** 酒桶数量 */
    @Excel(name = "酒桶数量")
    private Long bucketCount;

    /** 主要产品定价 */
    @Excel(name = "主要产品定价")
    private String mainPrice;

    /** 销售方式 */
    @Excel(name = "销售方式")
    private String salesMode;

    /** 获奖种类 */
    @Excel(name = "获奖种类")
    private String awards;

    /** 获奖信息 */
    @Excel(name = "获奖信息")
    private String awardInformation;

    /** 征集口号 */
    @Excel(name = "征集口号")
    private String slogan;
}
