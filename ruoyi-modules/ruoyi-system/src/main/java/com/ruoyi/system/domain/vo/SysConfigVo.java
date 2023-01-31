package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.excel.annotation.ExcelDictFormat;
import com.ruoyi.common.excel.convert.ExcelDictConvert;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 参数配置视图对象 sys_config
 *
 * @author ruoyi
 * @date 2023-01-31
 */
@Data
@ExcelIgnoreUnannotated
public class SysConfigVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 参数主键
     */
    @ExcelProperty(value = "参数主键")
    private Long configId;

    /**
     * 参数名称
     */
    @ExcelProperty(value = "参数名称")
    private String configName;

    /**
     * 参数键名
     */
    @ExcelProperty(value = "参数键名")
    private String configKey;

    /**
     * 参数键值
     */
    @ExcelProperty(value = "参数键值")
    private String configValue;

    /**
     * 系统内置（Y是 N否）
     */
    @ExcelProperty(value = "系统内置", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_yes_no")
    private String configType;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
