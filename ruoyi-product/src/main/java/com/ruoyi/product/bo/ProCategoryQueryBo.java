package com.ruoyi.product.bo;

import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品服务类别分页查询对象 pro_category
 *
 * @author ruoyi
 * @date 2021-07-01
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("商品服务类别分页查询对象")
public class ProCategoryQueryBo extends BaseEntity {

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
	 * 类别名字
	 */
	@ApiModelProperty("类别名字")
	private String categoryName;
	/**
	 * icon图片
	 */
	@ApiModelProperty("icon图片")
	private String iconUrl;

}
