package com.ruoyi.product.bo;

import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 商品详情分页查询对象 pro_item
 *
 * @author ruoyi
 * @date 2021-04-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("商品详情分页查询对象")
public class ProItemQueryBo extends BaseEntity {

	/**
	 * 分页大小
	 */
	@ApiModelProperty("分页大小")
	private Integer pageSize;
	/**
	 * 当前页数
	 */
	@ApiModelProperty("当前页数")
	private Integer pageNum;
	/**
	 * 排序列
	 */
	@ApiModelProperty("排序列")
	private String orderByColumn;
	/**
	 * 排序的方向desc或者asc
	 */
	@ApiModelProperty(value = "排序的方向", example = "asc,desc")
	private String isAsc;


	/**
	 * 产品名字
	 */
	@ApiModelProperty("产品名字")
	private String proName;
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

	@ApiModelProperty("是否分页，0不分页，1分页，默认分页")
	private Integer bePage = 1;

}
