package com.ruoyi.common.core.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 查询基类
 * @author Yjoioooo
 * @date 2021/6/6 19:55
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class QueryEntity implements Serializable {

	private static final long serialVersionUID = 1L;

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

	/** 请求参数 */
	private Map<String, Object> params = new HashMap<>();

}
