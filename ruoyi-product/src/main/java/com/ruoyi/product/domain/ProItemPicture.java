package com.ruoyi.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * proItemPicture对象 pro_item_picture
 *
 * @author ruoyi
 * @date 2021-03-20
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("pro_item_picture")
public class ProItemPicture implements Serializable {

    private static final long serialVersionUID=1L;


    /** 主键id */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /** 商品id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long itemId;

    /** 图片地址 */
    private String picUrl;

    /** 类型,1是商品banner图, 3是评论图片 */
    private Integer picType;

    /** paixu */
    private Integer picSort;

    /** $column.columnComment */
    private Date createTime;

    /** 创建人id */
    private String createBy;

    /** $column.columnComment */
    private Date updateTime;

    /** 更新人id */
    private String updateBy;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}