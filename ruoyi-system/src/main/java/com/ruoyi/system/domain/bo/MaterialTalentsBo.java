package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 材料关系业务对象 material_talents
 *
 * @author ruoyi
 * @date 2023-03-09
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class MaterialTalentsBo extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 父id
     */
    @NotNull(message = "父id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long parentId;

    /**
     * 值
     */
    @NotNull(message = "值不能为空", groups = { AddGroup.class, EditGroup.class })
    private String talentsValue;

    /**
     * 节点名称
     */
    @NotBlank(message = "节点名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String talentsName;

    /**
     * 是否选中
     */
//    @NotBlank(message = "是否选中不能为空", groups = { AddGroup.class, EditGroup.class })
    private String selected;

    /**
     * 对应的材料
     */
//    @NotBlank(message = "对应的材料不能为空", groups = { AddGroup.class, EditGroup.class })
    private String materials;

    private Integer[] materialList;


}
