package org.dromara.question.domain.bo;

import lombok.Data;
import org.dromara.question.domain.vo.OptionVo;

import java.io.Serializable;
import java.util.List;

/**
 * @author : lvxudong
 * @date : 2023/11/8 17:47
 * @className : TitleResp
 * @description :
 **/
@Data
public class TitleResp implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 题目
     */
    private String question;

    /**
     * 题目标签类型
     */
    private Long labelName;

    private List<OptionVo> optionVoList;
}
