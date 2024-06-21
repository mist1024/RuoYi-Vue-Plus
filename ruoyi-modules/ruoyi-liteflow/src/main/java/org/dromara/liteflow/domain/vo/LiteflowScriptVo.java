package org.dromara.liteflow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import org.dromara.liteflow.domain.LiteflowScript;

import java.io.Serial;
import java.io.Serializable;

/**
 * 脚本视图对象 liteflow_script
 *
 * @author AprilWind
 * @date 2024-06-21
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = LiteflowScript.class)
public class LiteflowScriptVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ExcelProperty(value = "id")
    private Long id;

    /**
     * 配置环境
     */
    @ExcelProperty(value = "配置环境")
    private String applicationName;

    /**
     * 脚本ID
     */
    @ExcelProperty(value = "脚本ID")
    private String scriptId;

    /**
     * 脚本名称
     */
    @ExcelProperty(value = "脚本名称")
    private String scriptName;

    /**
     * 脚本语言类型（groovy | qlexpress | js | python | lua | aviator | java | kotlin）
     */
    @ExcelProperty(value = "脚本语言类型", converter = ExcelDictConvert.class)
    private String scriptLanguage;

    /**
     * 脚本类型(script普通脚本节点，脚本里无需返回 switch_script选择脚本节点，脚本里需要返回选择的节点Id boolean_script条件脚本节点，脚本里需要返回true/false for_script数量循环节点，脚本里需要返回数值类型，表示循环次数)
     */
    @ExcelProperty(value = "脚本类型")
    private String scriptType;

    /**
     * 状态（0未激活 1激活）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=未激活,1=激活")
    private Integer scriptStatus;

    /**
     * 脚本内容
     */
    @ExcelProperty(value = "脚本内容")
    private String scriptData;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

}
