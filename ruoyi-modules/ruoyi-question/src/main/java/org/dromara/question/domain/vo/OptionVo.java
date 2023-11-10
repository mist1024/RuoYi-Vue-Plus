package org.dromara.question.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.question.domain.Options;

import java.io.Serializable;

/**
 * @author : lvxudong
 * @date : 2023/11/8 17:37
 * @className : OptionVo
 * @description :
 **/
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Options.class)
public class OptionVo implements Serializable {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 选项序号
     */
    private Integer serial;

    /**
     * 题目id
     */
    private Long questionId;

    /**
     * 选项内容
     */
    private String optionContent;
}
