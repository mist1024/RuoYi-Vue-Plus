package com.ruoyi.demo.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.DropDown;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 带有下拉选的Excel导出
 *
 * @author Emil.Zhang
 */
@Data
@ExcelIgnoreUnannotated
@AllArgsConstructor
@NoArgsConstructor
public class ExportDemoVo {

    private static final long serialVersionUID = 1L;

    /**
     * 用户昵称
     */
    @ExcelProperty(value = "用户昵称", index = 0)
    private String nickName;

    /**
     * 性别
     * <p>
     * 使用DropDown形式注入的下拉选可以使用任意形式，自己能解析出来就行
     */
    @ExcelProperty(value = "性别", index = 1)
    @DropDown({"1=男", "2=女"})
    private String genderStr;

    /**
     * 数据库中的性别
     */
    private Integer gender;

    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号", index = 2)
    private String phoneNumber;

    /**
     * Email
     */
    @ExcelProperty(value = "Email", index = 3)
    private String email;

    /**
     * 省
     * <p>
     * 级联下拉
     */
    @ExcelProperty(value = "省", index = 25)
    private String province;

    /**
     * 数据库中的省ID
     */
    private Integer provinceId;

    /**
     * 市
     * <p>
     * 级联下拉
     */
    @ExcelProperty(value = "市", index = 26)
    private String city;

    /**
     * 数据库中的市ID
     */
    private Integer cityId;

    /**
     * 县
     * <p>
     * 级联下拉
     */
    @ExcelProperty(value = "县", index = 27)
    private String area;

    /**
     * 数据库中的县ID
     */
    private Integer areaId;
}
