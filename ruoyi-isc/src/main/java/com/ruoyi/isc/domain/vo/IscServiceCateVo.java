package com.ruoyi.isc.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 服务分类视图对象 isc_service_cate
 *
 * @author wenchao gong
 * @date 2021-08-22
 */
@Data
@ApiModel("服务分类视图对象")
@ExcelIgnoreUnannotated
public class IscServiceCateVo {

    private static final long serialVersionUID = 1L;

    /**
     *  分类ID
     */
    @ApiModelProperty("分类ID")
    private Long cateId;

    /**
     * 父分类ID
     */
    @ExcelProperty(value = "父分类ID")
    @ApiModelProperty("父分类ID")
    private Long parentId;

    /**
     * 分类名称
     */
    @ExcelProperty(value = "分类名称")
    @ApiModelProperty("分类名称")
    private String cateName;

    /**
     * 启用状态（0启用 1停用）
     */
    @ExcelProperty(value = "启用状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_normal_disable")
    @ApiModelProperty("启用状态（0启用 1停用）")
    private String enabled;

    /**
     * 显示顺序
     */
    @ExcelProperty(value = "显示顺序")
    @ApiModelProperty("显示顺序")
    private Long orderNum;

    /**
     * 更新者
     */
    @ExcelProperty(value = "更新者")
    @ApiModelProperty("更新者")
    private String updateBy;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    @ApiModelProperty("备注")
    private String remark;


}
