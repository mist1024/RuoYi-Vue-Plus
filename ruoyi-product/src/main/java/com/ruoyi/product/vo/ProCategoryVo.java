package com.ruoyi.product.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @BelongsProject: RuoYi-Vue-Plus
 * @BelongsPackage: com.ruoyi.product.vo
 * @Author: yefei
 * @CreateTime: 2021-07-01 12:00
 * @Description:
 */
@Data
@ApiModel("商品服务类别视图对象")
public class ProCategoryVo {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@ApiModelProperty("主键id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 类别名字
	 */
	@Excel(name = "类别名字")
	@ApiModelProperty("类别名字")
	private String categoryName;

	/**
	 * icon图片
	 */
	@Excel(name = "icon图片")
	@ApiModelProperty("icon图片")
	private String iconUrl;

	/**
	 * 备注信息
	 */
	@Excel(name = "备注信息")
	@ApiModelProperty("备注信息")
	private String remark;
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
