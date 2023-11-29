package org.dromara.question.domain.vo;

import org.dromara.question.domain.Rewards;
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
 * 奖品管理视图对象 f_rewards
 *
 * @author lvxudong
 * @date 2023-11-27
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Rewards.class)
public class RewardsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 奖励类型
     */
    private Integer type;
    @ExcelProperty(value = "奖励类型")
    private String typeName;

    /**
     * 奖励名称
     */
    @ExcelProperty(value = "奖励名称")
    private String name;

    /**
     * 奖励图片
     */
    @ExcelProperty(value = "奖励图片")
    private String image;

    /**
     * 图片描述
     */
    @ExcelProperty(value = "图片描述")
    private String imgDescribe;


}
