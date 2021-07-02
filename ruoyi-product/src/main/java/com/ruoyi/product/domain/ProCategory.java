package com.ruoyi.product.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 商品服务类别对象 pro_category
 *
 * @author ruoyi
 * @date 2021-03-14
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("pro_category")
public class ProCategory implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 类别名字
     */
    @Excel(name = "类别名字")
    private String categoryName;

    /**
     * icon图片
     */
    @Excel(name = "icon图片")
    private String iconUrl;

    /**
     * 备注信息
     */
    @Excel(name = "备注信息")
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

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
