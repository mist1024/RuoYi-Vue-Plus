package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

import com.ruoyi.common.mybatis.core.domain.BaseEntity;

/**
 * 字典类型业务对象 sys_dict_type
 *
 * @author ruoyi
 * @date 2023-02-01
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictTypeBo extends BaseEntity {

    /**
     * 字典主键
     */
    @NotNull(message = "字典主键不能为空", groups = { EditGroup.class })
    private Long dictId;

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空", groups = { AddGroup.class, EditGroup.class })
    @Size(min = 0, max = 100, message = "字典类型名称长度不能超过{max}个字符")
    private String dictName;

    /**
     * 字典类型
     */
    @NotBlank(message = "字典类型不能为空", groups = { AddGroup.class, EditGroup.class })
    @Size(min = 0, max = 100, message = "字典类型类型长度不能超过{max}个字符")
    @Pattern(regexp = "^[a-z][a-z0-9_]*$", message = "字典类型必须以字母开头，且只能为（小写字母，数字，下滑线）")
    private String dictType;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 创建部门
     */
    private Long createDept;

    /**
     * 备注
     */
    private String remark;


}
