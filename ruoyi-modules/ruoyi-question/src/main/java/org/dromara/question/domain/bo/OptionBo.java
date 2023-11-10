package org.dromara.question.domain.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : lvxudong
 * @date : 2023/11/10 16:44
 * @className : OptionBo
 * @description :
 **/
@Data
public class OptionBo implements Serializable {
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
