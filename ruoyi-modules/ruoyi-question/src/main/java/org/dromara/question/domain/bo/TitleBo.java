package org.dromara.question.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.question.domain.Title;

/**
 * 题目业务对象 f_questions
 *
 * @author Lion Li
 * @date 2023-11-08
 */
@Data
@AutoMapper(target = Title.class, reverseConvertGenerate = false)
public class TitleBo {

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
