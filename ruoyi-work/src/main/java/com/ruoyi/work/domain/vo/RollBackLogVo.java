package com.ruoyi.work.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;



/**
 * 撤销记录视图对象 roll_back_log
 *
 * @author ruoyi
 * @date 2023-03-07
 */
@Data
@ExcelIgnoreUnannotated
public class RollBackLogVo {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 撤销时间
     */
    @ExcelProperty(value = "撤销时间")
    private Date createTime;

    /**
     * 是后台撤回还是前台撤回
     */
    @ExcelProperty(value = "是后台撤回还是前台撤回")
    private String type;

    /**
     * 撤销原因
     */
    @ExcelProperty(value = "撤销原因")
    private String reply;

    /**
     * 撤销人id
     */
    @ExcelProperty(value = "撤销人id")
    private String createBy;

    /**
     * 将运行表中的数据保存
     */
    @ExcelProperty(value = "将运行表中的数据保存")
    private String param;


}
