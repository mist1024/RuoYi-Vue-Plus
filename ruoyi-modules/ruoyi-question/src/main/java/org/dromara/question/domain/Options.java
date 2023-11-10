package org.dromara.question.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;

import java.io.Serial;
import java.util.Date;

/**
 * @author : lvxudong
 * @date : 2023/11/8 17:39
 * @className : Options
 * @description :
 **/
@Data
@TableName("f_options")
public class Options {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 选项序号
     */
    private Integer serial;

    /**
     * 题目id
     */
    private Long questionId;

    /**
     * 选项内容
     */
    private String optionContent;

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
