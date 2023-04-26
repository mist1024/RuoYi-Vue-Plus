package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 预约导出视图对象 subscribe_export
 *
 * @author ruoyi
 * @date 2023-04-20
 */
@Data
@ExcelIgnoreUnannotated
public class SubscribeExportVo {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 路径
     */
    @ExcelProperty(value = "路径")
    private String path;

    /**
     * 申请人id
     */
    @ExcelProperty(value = "申请人id")
    private String userId;

    private String description;

    private String processKey;


}
