package org.dromara.question.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;

import java.io.Serial;

/**
 * @author : lvxudong
 * @date : 2023/11/8 17:39
 * @className : Options
 * @description :
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("f_options")
public class Options extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
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
    private Long optionContent;
}
