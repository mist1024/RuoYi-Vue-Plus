package com.ruoyi.product.bo;

import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @BelongsProject: RuoYi-Vue-Plus
 * @BelongsPackage: com.ruoyi.product.bo
 * @Author: yefei
 * @CreateTime: 2021-06-30 16:37
 * @Description:
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("banner管理分页查询对象")
public class ProBannerQueryBo extends BaseEntity {
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
