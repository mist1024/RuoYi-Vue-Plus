package org.dromara.question.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 题目标签对象 f_labels
 *
 * @author Lion Li
 * @date 2023-11-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("f_labels")
public class Labels extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 标签名称
     */
    private String label;

    /**
     * 标签状态 1：启用 0：禁用
     */
    private Integer status;


}
