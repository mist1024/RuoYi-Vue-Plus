package com.ruoyi.work.domain.bo;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 撤销记录业务对象 roll_back_log
 *
 * @author ruoyi
 * @date 2023-03-07
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class RollBackLogBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 是后台撤回还是前台撤回
     */
    @NotBlank(message = "是后台撤回还是前台撤回不能为空", groups = { AddGroup.class, EditGroup.class })
    private String type;

    /**
     * 撤销原因
     */
    @NotBlank(message = "撤销原因不能为空", groups = { AddGroup.class, EditGroup.class })
    private String reply;


    /**
     * 将运行表中的数据保存
     */
    @NotBlank(message = "将运行表中的数据保存不能为空", groups = { AddGroup.class, EditGroup.class })
    private String param;


}
