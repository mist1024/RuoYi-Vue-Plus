package com.ruoyi.product.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 商品服务类别添加对象 pro_category
 *
 * @author ruoyi
 * @date 2021-07-01
 */
@Data
@ApiModel("商品服务类别添加对象")
public class ProCategoryAddBo {


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
}
