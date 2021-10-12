package com.ruoyi.demo.domain.vo;

import lombok.Data;

/**
 * @author qianlan
 * @version 1.0
 * @date 2021/10/11 19:16
 */
@Data
public class PictureListVo {
	/**
	 * 主键
	 */
	private Long picId;

	/**
	 * url地址
	 */
	private String picUrl;

	/**
	 * 图片选择
	 */
	private Long picSelection;
}
