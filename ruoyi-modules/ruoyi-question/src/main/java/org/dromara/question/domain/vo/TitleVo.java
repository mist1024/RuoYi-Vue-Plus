package org.dromara.question.domain.vo;

import org.dromara.question.domain.Title;
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
 * 题目视图对象 f_questions
 *
 * @author Lion Li
 * @date 2023-11-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Title.class)
public class TitleVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 题目
     */
    @ExcelProperty(value = "题目")
    private String question;

    /**
     * 题目标签类型
     */
    @ExcelProperty(value = "题目标签类型")
    private Long labelId;


}
