package org.dromara.question.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.util.Date;

/**
 * 题目标签对象 f_labels
 *
 * @author Lion Li
 * @date 2023-11-08
 */
@Data
@TableName("f_labels")
public class Labels {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标签名称
     */
    private String label;

    /**
     * 标签状态 1：启用 0：禁用
     */
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
