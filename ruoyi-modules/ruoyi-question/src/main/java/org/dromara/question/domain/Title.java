package org.dromara.question.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 题目对象 f_questions
 *
 * @author Lion Li
 * @date 2023-11-08
 */
@Data
@TableName("f_questions")
public class Title {

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 题目
     */
    private String question;

    /**
     * 题目标签类型
     */
    private Long labelId;

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
