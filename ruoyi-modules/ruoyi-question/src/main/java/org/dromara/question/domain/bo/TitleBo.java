package org.dromara.question.domain.bo;

import org.dromara.question.domain.Title;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 题目业务对象 f_questions
 *
 * @author Lion Li
 * @date 2023-11-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Title.class, reverseConvertGenerate = false)
public class TitleBo extends BaseEntity {

    /**
     * 
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 题目
     */
    @NotBlank(message = "题目不能为空", groups = { AddGroup.class, EditGroup.class })
    private String question;

    /**
     * 题目标签类型
     */
    @NotNull(message = "题目标签类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long labelId;


}
