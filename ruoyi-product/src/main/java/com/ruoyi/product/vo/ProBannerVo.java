package com.ruoyi.product.vo;

/**
 * @BelongsProject: RuoYi-Vue-Plus
 * @BelongsPackage: com.ruoyi.product.vo
 * @Author: yefei
 * @CreateTime: 2021-06-30 16:37
 * @Description:
 */

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * banner管理视图对象 pro_banner
 *
 * @author ruoyi
 * @date 2021-06-30
 */
@Data
@ApiModel("banner管理视图对象")
public class ProBannerVo {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty("主键id")
	private Long id;

	/**
	 * banner标题
	 */
	@Excel(name = "banner标题")
	@ApiModelProperty("banner标题")
	private String title;

	/**
	 * url跳转(也可能是path)
	 */
	@Excel(name = "url跳转(也可能是path)")
	@ApiModelProperty("url跳转(也可能是path)")
	private String url;

	/**
	 * 图片url
	 */
	@Excel(name = "图片url")
	@ApiModelProperty("图片url")
	private String picUrl;

	/**
	 * 是否跳转
	 */
	@Excel(name = "是否跳转")
	@ApiModelProperty("是否跳转")
	private Integer redirectFlag;

	/**
	 * banner类型,1应用内跳转,3外部跳转
	 */
	@Excel(name = "banner类型,1应用内跳转,3外部跳转")
	@ApiModelProperty("banner类型,1应用内跳转,3外部跳转")
	private Integer bannerType;


}
