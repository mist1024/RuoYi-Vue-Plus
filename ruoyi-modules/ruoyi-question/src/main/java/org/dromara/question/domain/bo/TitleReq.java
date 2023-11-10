package org.dromara.question.domain.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.question.domain.vo.OptionVo;

import java.io.Serializable;
import java.util.List;

/**
 * @author : lvxudong
 * @date : 2023/11/10 16:41
 * @className : TitleReq
 * @description :
 **/
@Data
public class TitleReq implements Serializable {
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

    List<OptionVo> optionList;
}
