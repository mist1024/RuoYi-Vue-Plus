package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;


/**
 * 购房审核人员不同类型需要的审核人员名单视图对象 buy_house_check
 *
 * @author ruoyi
 * @date 2023-02-24
 */
@Data
@ExcelIgnoreUnannotated
public class BuyHouseCheckVo {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String remark;


    /**
     * 人员
     */
    @ExcelProperty(value = "人员")
    private String person;

    /**
     * 需要关联的key
     */
    @ExcelProperty(value = "需要关联的key")
    private String otherKey;

    /**
     * 关键key
     */
    @ExcelProperty(value = "关键key")
    private String cruxKey;

    private String step;


}
