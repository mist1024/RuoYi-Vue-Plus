package com.ruoyi.product.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 商品关系（预约记录、优惠券）添加对象 pro_item_relation
 *
 * @author ruoyi
 * @date 2021-04-09
 */
@Data
@ApiModel("商品关系（预约记录、优惠券）添加对象")
public class ProItemRelationAddBo {

    /** 业务id，可以是预约记录id，也可以是优惠券id */
    @ApiModelProperty("业务id，可以是预约记录id，也可以是优惠券id")
    private Long businessId;
    /** 1表示优惠券类型关联关系，3表示预约类型关联关系 */
    @ApiModelProperty("1表示优惠券类型关联关系，3表示预约类型关联关系")
    private String type;
    /** 商品id */
    @ApiModelProperty("商品id")
    private Long proItemId;
    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;
    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createBy;
    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;
    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updateBy;
}
