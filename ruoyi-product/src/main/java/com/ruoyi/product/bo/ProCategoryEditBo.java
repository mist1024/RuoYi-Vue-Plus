package com.ruoyi.product.bo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 商品服务类别编辑对象 pro_category
 *
 * @author ruoyi
 * @date 2021-07-01
 */
@Data
@ApiModel("商品服务类别编辑对象")
public class ProCategoryEditBo {


	/**
	 * 主键id
	 */
	@ApiModelProperty("主键id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

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

	/**
	 * 备注信息
	 */
	@ApiModelProperty("备注信息")
	private String remark;
}
