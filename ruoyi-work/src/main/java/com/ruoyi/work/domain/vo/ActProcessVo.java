package com.ruoyi.work.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;

import java.util.Date;



/**
 * 流程运作视图对象 act_process
 *
 * @author ruoyi
 * @date 2023-02-03
 */
@Data
@ExcelIgnoreUnannotated
public class ActProcessVo {

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

    /**
     * 审核类型（1，人员，2.部门，3角色）
     */
    @ExcelProperty(value = "审核类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "1=，人员，2.部门，3角色")
    private String checkType;

    /**
     * 步骤
     */
    @ExcelProperty(value = "步骤")
    private String step;

    private Date createTime=new Date();

    /**
     * 是由存在下一步骤
     */
    @ExcelProperty(value = "是由存在下一步骤")
    private Boolean isNext;

    private String type;

    private String startUser;

    private String userId;

    private String companyId;

    private String description;

    /**
     * 证件号码
     */
    private  String cardId;

    private String companyName;


}
