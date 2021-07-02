package com.ruoyi.product.vo;

import com.ruoyi.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 商品关系（预约记录、优惠券）视图对象 mall_package
 *
 * @author ruoyi
 * @date 2021-04-09
 */
@Data
@ApiModel("商品关系（预约记录、优惠券）视图对象")
public class ProItemRelationVo {
	private static final long serialVersionUID = 1L;

	/** 主键id */
	@ApiModelProperty("主键id")
	private Long id;

	/** 业务id，可以是预约记录id，也可以是优惠券id */
	@Excel(name = "业务id，可以是预约记录id，也可以是优惠券id")
	@ApiModelProperty("业务id，可以是预约记录id，也可以是优惠券id")
	private Long businessId;
	/** 1表示优惠券类型关联关系，3表示预约类型关联关系 */
	@Excel(name = "1表示优惠券类型关联关系，3表示预约类型关联关系")
	@ApiModelProperty("1表示优惠券类型关联关系，3表示预约类型关联关系")
	private String type;
	/** 商品id */
	@Excel(name = "商品id")
	@ApiModelProperty("商品id")
	private Long proItemId;

}
