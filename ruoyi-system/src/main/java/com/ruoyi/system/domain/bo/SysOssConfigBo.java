package com.ruoyi.system.domain.bo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 云存储配置业务对象 sys_oss_config
 *
 * @author ruoyi
 * @date 2021-08-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("云存储配置业务对象")
public class SysOssConfigBo extends BaseEntity {

	/**
	 * 主建
	 */
	@ApiModelProperty("主建")
	private Integer ossConfigId;
    /**
     * 配置key
     */
    @ApiModelProperty(value = "配置key", required = true)
    @NotBlank(message = "配置key不能为空", groups = { AddGroup.class, EditGroup.class })
    private String configKey;

    /**
     * accessKey
     */
    @ApiModelProperty(value = "accessKey")
    private String accessKey;

    /**
     * 秘钥
     */
    @ApiModelProperty(value = "secretKey")
    private String secretKey;

    /**
     * 桶名称
     */
    @ApiModelProperty(value = "桶名称")
    private String bucketName;

    /**
     * 前缀
     */
    @ApiModelProperty(value = "前缀")
    private String prefix;

    /**
     * 访问站点
     */
    @ApiModelProperty(value = "访问站点")
    private String endpoint;

    /**
     * 是否htpps（0否 1是）
     */
    @ApiModelProperty(value = "是否htpps（0否 1是）")
    private String isHttps;

    /**
     * 域
     */
    @ApiModelProperty(value = "域")
    private String region;

    /**
     * 扩展字段
     */
    @ApiModelProperty(value = "扩展字段")
    private String ext1;


    /**
     * 分页大小
     */
    @ApiModelProperty("分页大小")
    private Integer pageSize;

    /**
     * 当前页数
     */
    @ApiModelProperty("当前页数")
    private Integer pageNum;

    /**
     * 排序列
     */
    @ApiModelProperty("排序列")
    private String orderByColumn;

    /**
     * 排序的方向desc或者asc
     */
    @ApiModelProperty(value = "排序的方向", example = "asc,desc")
    private String isAsc;

}
