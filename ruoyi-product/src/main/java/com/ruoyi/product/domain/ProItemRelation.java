package com.ruoyi.product.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;

/**
 * 商品关系（预约记录、优惠券）对象 pro_item_relation
 * 
 * @author ruoyi
 * @date 2021-04-09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("pro_item_relation")
public class ProItemRelation implements Serializable {

private static final long serialVersionUID=1L;


    /** 主键id */
    @TableId(value = "id", type = IdType.INPUT)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 业务id，可以是预约记录id，也可以是优惠券id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long businessId;

    /** 1表示优惠券类型关联关系，3表示预约类型关联关系 */
    private String type;

    /** 商品id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long proItemId;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 创建人 */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 更新人 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    public String getStringForProItemId() {
        return this.proItemId.toString();
    }


}
