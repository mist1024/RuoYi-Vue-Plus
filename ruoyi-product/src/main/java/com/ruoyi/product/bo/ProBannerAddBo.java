package com.ruoyi.product.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * banner管理添加对象 pro_banner
 *
 * @author ruoyi
 * @date 2021-06-30
 */
@Data
@ApiModel("banner管理添加对象")
public class ProBannerAddBo {


	/**
	 * 主键id
	 */
	@ApiModelProperty("主键id")
	private Long id;

	/**
	 * banner标题
	 */
	@ApiModelProperty("banner标题")
	private String title;

	/**
	 * url跳转(也可能是path)
	 */
	@ApiModelProperty("url跳转(也可能是path)")
	private String url;

	/**
	 * 图片url
	 */
	@ApiModelProperty("图片url")
	private String picUrl;

	/**
	 * 是否跳转
	 */
	@ApiModelProperty("是否跳转")
	private Integer redirectFlag;

	/**
	 * banner类型,1应用内跳转,3外部跳转
	 */
	@ApiModelProperty("banner类型,1应用内跳转,3外部跳转")
	private Integer bannerType;
}
