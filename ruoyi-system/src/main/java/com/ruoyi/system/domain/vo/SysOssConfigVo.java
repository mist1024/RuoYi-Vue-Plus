package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 云存储配置视图对象 sys_oss_config
 *
 * @author ruoyi
 * @date 2021-08-11
 */
@Data
@ApiModel("云存储配置视图对象")
@ExcelIgnoreUnannotated
public class SysOssConfigVo {

	private static final long serialVersionUID = 1L;

	/**
     *  主建
     */
	@ApiModelProperty("主建")
	private Integer ossConfigId;

    /**
     * 配置key
     */
	@ExcelProperty(value = "配置key")
	@ApiModelProperty("配置key")
	private String configKey;

    /**
     * accessKey
     */
	@ExcelProperty(value = "accessKey")
	@ApiModelProperty("accessKey")
	private String accessKey;

    /**
     * 秘钥
     */
	@ExcelProperty(value = "secretKey")
	@ApiModelProperty("secretKey")
	private String secretKey;

    /**
     * 桶名称
     */
	@ExcelProperty(value = "桶名称")
	@ApiModelProperty("桶名称")
	private String bucketName;

    /**
     * 前缀
     */
	@ExcelProperty(value = "前缀")
	@ApiModelProperty("前缀")
	private String prefix;

    /**
     * 访问站点
     */
	@ExcelProperty(value = "访问站点")
	@ApiModelProperty("访问站点")
	private String endpoint;

    /**
     * 是否htpps（0否 1是）
     */
    @ExcelProperty(value = "是否htpps", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_yes_no")
	@ApiModelProperty("是否htpps（0否 1是）")
	private String isHttps;

    /**
     * 域
     */
	@ExcelProperty(value = "域")
	@ApiModelProperty("域")
	private String region;

    /**
     * 状态(0正常 1停用)
     */
	@ExcelProperty(value = "状态(0正常 1停用)")
	@ApiModelProperty("状态(0正常 1停用)")
	private String status;

    /**
     * 扩展字段
     */
	@ExcelProperty(value = "扩展字段")
	@ApiModelProperty("扩展字段")
	private String ext1;


}
