package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 推送日志视图对象 push_log
 *
 * @author ruoyi
 * @date 2023-07-20
 */
@Data
@ExcelIgnoreUnannotated
public class PushLogVo {

    private static final long serialVersionUID = 1L;

    /**
     * 推送数据
     */
    @ExcelProperty(value = "推送数据")
    private String pushData;

    /**
     * 返回结果
     */
    @ExcelProperty(value = "返回结果")
    private String resultData;


}
