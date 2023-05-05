package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;

import java.util.ArrayList;


/**
 * 材料模块视图对象 material_module
 *
 * @author ruoyi
 * @date 2023-03-09
 */
@Data
@ExcelIgnoreUnannotated
public class MaterialModuleVo {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 材料名称
     */
    @ExcelProperty(value = "材料名称")
    private String materialName;

    /**
     * 材料key
     */
    @ExcelProperty(value = "材料key")
    private String materialKey;

    /**
     * 审核部门
     */
    @ExcelProperty(value = "审核部门")
    private String auditDept;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String description;

    /**
     * 排序
     */
    @ExcelProperty(value = "排序")
    private Long sort;

    /**
     * 是否必填
     */
    @ExcelProperty(value = "是否必填", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_yes_no")
    private String isMust;

    /**
     * 上传材料
     */
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
    private Long[] auditDeptArr = new Long[]{};

    /**
     * 按钮显示名称
     */
    private String interfaceName;

}
