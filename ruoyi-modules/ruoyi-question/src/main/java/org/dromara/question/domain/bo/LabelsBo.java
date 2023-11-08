package org.dromara.question.domain.bo;

import org.dromara.question.domain.Labels;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 题目标签业务对象 f_labels
 *
 * @author Lion Li
 * @date 2023-11-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Labels.class, reverseConvertGenerate = false)
public class LabelsBo extends BaseEntity {

    /**
     * 
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 标签名称
     */
    @NotBlank(message = "标签名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String label;

    /**
     * 标签状态 1：启用 0：禁用
     */
    @NotNull(message = "标签状态 1：启用 0：禁用不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer status;


}
