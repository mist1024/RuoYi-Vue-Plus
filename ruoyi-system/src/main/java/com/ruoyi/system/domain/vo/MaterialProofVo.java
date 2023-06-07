package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 材料视图对象 material_proof
 *
 * @author ruoyi
 * @date 2023-03-15
 */
@Data
@ExcelIgnoreUnannotated
public class MaterialProofVo {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 关联申请id
     */
    @ExcelProperty(value = "关联申请id")
    private String houseId;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 0待审核，1审核失败，2审核成功
     */
    @ExcelProperty(value = "0待审核，1审核失败，2审核成功")
    private Long status;

    /**
     * 所对应材料的id
     */
    @ExcelProperty(value = "所对应材料的id")
    private String modulePathId;

    /**
     * 数据
     */
    @ExcelProperty(value = "数据")
    private String file;

    /**
     * 对应module_paht的字段
     */
    @ExcelProperty(value = "对应module_paht的字段")
    private String materialKey;

    /**
     * 对应module_path的描述
     */
    @ExcelProperty(value = "对应module_path的描述")
    private String description;

    /**
     * 审核人
     */
    @ExcelProperty(value = "审核人")
    private String auditDept;

    /**
     * 审核人类型
     */
    @ExcelProperty(value = "审核人类型")
    private String checkType;

    private String materialName;

    private Integer number;

}
