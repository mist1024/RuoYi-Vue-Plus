package com.ruoyi.system.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 材料模块业务对象 material_module
 *
 * @author ruoyi
 * @date 2023-03-09
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class MaterialModuleBo extends BaseEntity {

    /**
     *
     */
    private Long id;

    /**
     * 材料名称
     */
    @NotBlank(message = "材料名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String materialName;

    /**
     * 材料key
     */
    @NotBlank(message = "材料key不能为空", groups = { AddGroup.class, EditGroup.class })
    private String materialKey;

    /**
     * 审核部门
     */
//    @NotBlank(message = "审核部门不能为空", groups = { AddGroup.class, EditGroup.class })
    private String auditDept;

    @TableField(exist = false)
    private Long[] auditDeptArr =new Long[]{};

    /**
     * 描述
     */
    private String description;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long sort;

    /**
     * 是否必填
     */
    @NotNull(message = "是否必填不能为空", groups = { AddGroup.class, EditGroup.class })
    private String isMust;

    /**
     * 接口或者全路径地址
     */
    private String interfacePath;

    /**
     * 1是接口,2是路径
     */
    private String interfaceType;

    /**
     * 按钮显示名称
     */
    private String interfaceName;


}
