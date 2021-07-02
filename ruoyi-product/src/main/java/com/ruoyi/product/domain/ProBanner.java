package com.ruoyi.product.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * banner管理对象 pro_banner
 *
 * @author ruoyi
 * @date 2021-03-14
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("pro_banner")
public class ProBanner implements Serializable {

	private static final long serialVersionUID = 1L;


	/**
	 * 主键id
	 */
	@TableId(value = "id")
	private Long id;

	/**
	 * banner标题
	 */
	private String title;

	/**
	 * url跳转(也可能是path)
	 */
	private String url;

	/**
	 * 图片url
	 */
	private String picUrl;

	/**
	 * 是否跳转
	 */
	private Integer redirectFlag;

	/**
	 * banner类型,1应用内跳转,3外部跳转
	 */
	private Integer bannerType;

	/**
	 * 创建者
	 */
	@TableField(fill = FieldFill.INSERT)
	private String createBy;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 更新者
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateBy;

	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;
}
