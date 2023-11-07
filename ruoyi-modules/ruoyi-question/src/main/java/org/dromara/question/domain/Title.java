package org.dromara.question.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 题目对象 f_questions
 *
 * @author Lion Li
 * @date 2023-11-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("f_questions")
public class Title extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 题目
     */
    private String question;

    /**
     * 题目标签类型
     */
    private Long labelId;


}
