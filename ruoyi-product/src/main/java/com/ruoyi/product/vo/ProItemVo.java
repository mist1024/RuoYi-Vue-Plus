package com.ruoyi.product.vo;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.product.dto.ProItemPictureDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品详情视图对象 pro_item
 *
 * @author ruoyi
 * @date 2021-07-01
 */
@Data
@ApiModel("商品详情视图对象")
public class ProItemVo {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@ApiModelProperty("主键id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 产品名字
	 */
	@Excel(name = "产品名字")
	@ApiModelProperty("产品名字")
	private String proName;

	/**
	 * 价钱
	 */
	@Excel(name = "价钱")
	@ApiModelProperty("价钱")
	private BigDecimal price;

	/**
	 * 描述
	 */
	@Excel(name = "描述")
	@ApiModelProperty("描述")
	private String description;

	/**
	 * 库存(限量库存)
	 */
	@Excel(name = "库存(限量库存)")
	@ApiModelProperty("库存(限量库存)")
	private Integer sku;

	/**
	 * 封面图片
	 */
	@Excel(name = "封面图片")
	@ApiModelProperty("封面图片")
	private String coverImage;

	/**
	 * 是否发布
	 */
	@Excel(name = "是否发布")
	@ApiModelProperty("是否发布")
	private Integer publishFlag;

	/**
	 * 发布时间
	 */
	@Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
	@ApiModelProperty("发布时间")
	private Date publishTime;

	/**
	 * 创建者
	 */
	@Excel(name = "创建者")
	@ApiModelProperty("创建者")
	private String createBy;

	/**
	 * 更新时间
	 */
	@Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
	@ApiModelProperty("更新时间")
	private Date updateTime;

	@ApiModelProperty("图片列表")
	private List<ProItemPictureDTO> proItemPictures;

	@ApiModelProperty("分类id集合")
	private List<String> proItemCategoryIds;

}
