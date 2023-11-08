package org.dromara.question.domain.vo;

import org.dromara.question.domain.Labels;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 题目标签视图对象 f_labels
 *
 * @author Lion Li
 * @date 2023-11-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Labels.class)
public class LabelsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 标签名称
     */
    @ExcelProperty(value = "标签名称")
    private String label;

    /**
     * 标签状态 1：启用 0：禁用
     */
    @ExcelProperty(value = "标签状态 1：启用 0：禁用")
    private Integer status;


}
