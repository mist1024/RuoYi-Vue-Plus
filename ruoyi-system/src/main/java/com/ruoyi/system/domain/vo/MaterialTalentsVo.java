package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;


/**
 * 材料关系视图对象 material_talents
 *
 * @author ruoyi
 * @date 2023-03-09
 */
@Data
@ExcelIgnoreUnannotated
public class MaterialTalentsVo {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 父id
     */
    @ExcelProperty(value = "父id")
    private Long parentId;

    /**
     * 值
     */
    @ExcelProperty(value = "值")
    private String talentsValue;

    /**
     * 节点名称
     */
    @ExcelProperty(value = "节点名称")
    private String talentsName;

    /**
     * 是否选中
     */
    @ExcelProperty(value = "是否选中")
    private String selected;

    /**
     * 对应的材料
     */
    @ExcelProperty(value = "对应的材料")
    private String materials;

    private Integer[] materialList;

    private String step;
}
