package com.ruoyi.product.domain;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.product.dto.ProItemPictureDTO;
import com.ruoyi.product.vo.ProItemVo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品详情对象 pro_item
 *
 * @author ruoyi
 * @date 2021-03-13
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("pro_item")
public class ProItem implements Serializable {

	private static final long serialVersionUID = 1L;


	/**
	 * 主键id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(value = "id", type = IdType.INPUT)
	private Long id;

	/**
	 * 产品名字
	 */
	@Excel(name = "产品名字")
	private String proName;

	/**
	 * 价钱
	 */
	@Excel(name = "价钱")
	private BigDecimal price;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 库存(限量库存)
	 */
	@Excel(name = "库存(限量库存)")
	private Integer sku;

	/**
	 * 封面图片
	 */
	@Excel(name = "封面图片")
	private String coverImage;

	/**
	 * 是否发布
	 */
	@Excel(name = "是否发布")
	private Integer publishFlag;

	/**
	 * 发布时间
	 */
	@Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date publishTime;

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

	@TableField(exist = false)
	private Map<String, Object> params = new HashMap<>();


	public ProItemVo toVo(List<ProItemPictureDTO> pictures, List<String> categoryIds) {
		ProItemVo vo = new ProItemVo();
		BeanUtil.copyProperties(this, vo);
		vo.setProItemPictures(pictures);
		vo.setProItemCategoryIds(categoryIds);
		return vo;
	}
}
