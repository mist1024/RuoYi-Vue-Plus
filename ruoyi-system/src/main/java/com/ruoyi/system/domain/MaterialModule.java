package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 材料模块对象 material_module
 *
 * @author ruoyi
 * @date 2023-03-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("material_module")
public class MaterialModule extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 材料名称
     */
    private String materialName;
    /**
     * 材料key
     */
    private String materialKey;
    /**
     * 审核部门
     */
    private String auditDept;
    /**
     * 描述
     */
    private String description;
    /**
     * 排序
     */
    private Long sort;
    /**
     * 是否必填
     */
    private String isMust;

    @TableField(exist = false)
    private String file;


    /**
     * 接口或者全路径地址
     */
    private String interfacePath;

    /**
     * 1是接口,2是路径
     */
    private String interfaceType;

    @TableField(exist = false)
    private Long[] auditDeptArr=new Long[]{};

    /**
     * 按钮显示名称
     */
    private String interfaceName;

}
