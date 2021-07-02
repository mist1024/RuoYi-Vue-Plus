package com.ruoyi.product.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商品关系（预约记录、优惠券）分页查询对象 pro_item_relation
 *
 * @author ruoyi
 * @date 2021-04-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("商品关系（预约记录、优惠券）分页查询对象")
public class ProItemRelationQueryBo extends BaseEntity {

	/** 分页大小 */
	@ApiModelProperty("分页大小")
	private Integer pageSize;
	/** 当前页数 */
	@ApiModelProperty("当前页数")
	private Integer pageNum;
	/** 排序列 */
	@ApiModelProperty("排序列")
	private String orderByColumn;
	/** 排序的方向desc或者asc */
	@ApiModelProperty(value = "排序的方向", example = "asc,desc")
	private String isAsc;


	/** 业务id，可以是预约记录id，也可以是优惠券id */
	@ApiModelProperty("业务id，可以是预约记录id，也可以是优惠券id")
	private Long businessId;
	/** 1表示优惠券类型关联关系，3表示预约类型关联关系 */
	@ApiModelProperty("1表示优惠券类型关联关系，3表示预约类型关联关系")
	private String type;
	/** 商品id */
	@ApiModelProperty("商品id")
	private Long proItemId;

}
