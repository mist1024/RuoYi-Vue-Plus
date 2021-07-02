package com.ruoyi.product.bo;

import com.ruoyi.product.dto.ProItemPictureDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品详情添加对象 pro_item
 *
 * @author ruoyi
 * @date 2021-07-01
 */
@Data
@ApiModel("商品详情添加对象")
public class ProItemAddBo {


	/**
	 * 产品名字
	 */
	@ApiModelProperty("产品名字")
	private String proName;

	/**
	 * 价钱
	 */
	@ApiModelProperty("价钱")
	private BigDecimal price;

	/**
	 * 描述
	 */
	@ApiModelProperty("描述")
	private String description;

	/**
	 * 库存(限量库存)
	 */
	@ApiModelProperty("库存(限量库存)")
	private Integer sku;

	/**
	 * 封面图片
	 */
	@ApiModelProperty("封面图片")
	private String coverImage;

	/**
	 * 是否发布
	 */
	@ApiModelProperty("是否发布")
	private Integer publishFlag;

	/**
	 * 发布时间
	 */
	@ApiModelProperty("发布时间")
	private Date publishTime;

	/**
	 * 创建者
	 */
	@ApiModelProperty("创建者")
	private String createBy;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	private Date createTime;

	/**
	 * 更新者
	 */
	@ApiModelProperty("更新者")
	private String updateBy;

	/**
	 * 更新时间
	 */
	@ApiModelProperty("更新时间")
	private Date updateTime;

	@ApiModelProperty("图片列表")
	private List<ProItemPictureDTO> proItemPictures;

	@ApiModelProperty("分类id集合")
	private List<String> proItemCategoryIds;
}
