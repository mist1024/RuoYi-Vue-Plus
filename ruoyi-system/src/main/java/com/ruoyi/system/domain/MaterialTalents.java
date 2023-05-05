package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 材料关系对象 material_talents
 *
 * @author ruoyi
 * @date 2023-03-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("material_talents")
public class MaterialTalents extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 父id
     */
    private Long parentId;
    /**
     * 值
     */
    private String talentsValue;
    /**
     * 节点名称
     */
    private String talentsName;
    /**
     * 版本
     */
    @Version
    private Long version;
    /**
     * 删除标志
     */
    @TableLogic
    private Long delFlag;
    /**
     * 是否选中
     */
    private String selected;
    /**
     * 对应的材料
     */
    private String materials;

    @TableField(exist = false)
    private Integer[] materialList;


}
