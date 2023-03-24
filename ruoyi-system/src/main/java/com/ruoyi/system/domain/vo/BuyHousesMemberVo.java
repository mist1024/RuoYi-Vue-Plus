package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;


/**
 * 购房家属关系视图对象 buy_houses_member
 *
 * @author ruoyi
 * @date 2023-03-15
 */
@Data
@ExcelIgnoreUnannotated
public class BuyHousesMemberVo {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 户口簿内页
     */
    @ExcelProperty(value = "户口簿内页")
    private String insidepageUrl;

    /**
     * 购房申报id
     */
    @ExcelProperty(value = "购房申报id")
    private String buyHousesId;

    /**
     * 身份证正面
     */
    @ExcelProperty(value = "身份证正面")
    private String frontUrl;

    /**
     * 关系
     */
    @ExcelProperty(value = "关系")
    private String relation;

    /**
     * 身份证背面
     */
    @ExcelProperty(value = "身份证背面")
    private String reverseUrl;

    /**
     * 房屋记录
     */
    @ExcelProperty(value = "房屋记录")
    private String homeRecordUrl;

    /**
     * 证件号
     */
    @ExcelProperty(value = "证件号")
    private String cardId;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名")
    private String name;


}
