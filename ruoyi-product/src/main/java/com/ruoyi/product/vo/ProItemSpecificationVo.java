package com.ruoyi.product.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 商品规格视图对象 mall_package
 *
 * @author ruoyi
 * @date 2021-04-06
 */
@Data
@ApiModel("商品规格视图对象")
public class ProItemSpecificationVo {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@ApiModelProperty("主键id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 商品id
	 */
	@Excel(name = "商品id")
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty("商品id")
	private Long itemId;
	/**
	 * 规格名字
	 */
	@Excel(name = "规格名字")
	@ApiModelProperty("规格名字")
	private String specificationTitle;
	/**
	 * 图片url地址
	 */
	@Excel(name = "图片url地址")
	@ApiModelProperty("图片url地址")
	private String urlPath;
	/**
	 * 规格库存
	 */
	@Excel(name = "规格库存")
	@ApiModelProperty("规格库存")
	private Integer specificationSku;
	/**
	 * 价格
	 */
	@Excel(name = "价格")
	@ApiModelProperty("价格")
	private BigDecimal price;
	/**
	 * 别名
	 */
	@Excel(name = "别名")
	@ApiModelProperty("别名")
	private String aliasName;
	/**
	 * 描述
	 */
	@Excel(name = "描述")
	@ApiModelProperty("描述")
	private String description;
	/**
	 * 是否上架,0未上架,1上架,默认是0
	 */
	@Excel(name = "是否上架,0未上架,1上架,默认是0")
	@ApiModelProperty("是否上架,0未上架,1上架,默认是0")
	private String publishFlag;
	/**
	 * 上架时间
	 */
	@Excel(name = "上架时间", width = 30, dateFormat = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty("上架时间")
	private LocalDateTime publishTime;

}
