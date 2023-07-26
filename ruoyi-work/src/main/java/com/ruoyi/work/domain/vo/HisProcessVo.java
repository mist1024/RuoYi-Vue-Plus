package com.ruoyi.work.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;

import java.util.Date;


/**
 * 历史运作视图对象 his_process
 *
 * @author ruoyi
 * @date 2023-02-03
 */
@Data
@ExcelIgnoreUnannotated
public class HisProcessVo {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 流程表id
     */
    @ExcelProperty(value = "流程表id")
    private String processId;

    /**
     * 业务id
     */
    @ExcelProperty(value = "业务id")
    private String businessId;

    /**
     * 审核
     */
    @ExcelProperty(value = "审核")
    private String audit;

    @TableField(exist = false)
    private String audit1;

    /**
     * 审核类型（1，人员，2.部门，3角色）
     */
    @ExcelProperty(value = "审核类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "1=，人员，2.部门，3角色")
    private String checkType;

    /**
     * 1fail(失败)，2success（成功）
     */
    @ExcelProperty(value = "1fail(失败)，2success", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "成=功")
    private String status;

    private String step;

    private String type;

    private String companyName;

    private Date createTime;

    private Date endTime;

    private String startUser;

    @TableField(exist = false)
    private String bean;


    private String userId;

    private String description;

    /**
     * 证件号码
     */
    private  String cardId;


}
