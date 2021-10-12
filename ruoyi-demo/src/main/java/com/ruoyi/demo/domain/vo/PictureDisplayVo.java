package com.ruoyi.demo.domain.vo;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 图片展示视图对象 hjc_pic
 *
 * @author qianlan
 * @date 2021-10-10
 */
@Data
@ApiModel("图片展示视图对象")
public class PictureDisplayVo {

	private static final long serialVersionUID = 1L;

	/**
     *  主键
     */
	@ApiModelProperty("主键")
	private Long picId;

    /**
     * url地址
     */
	@Excel(name = "url地址")
	@ApiModelProperty("url地址")
	private String picUrl;

    /**
     * 图片选择
     */
	@Excel(name = "图片选择")
	@ApiModelProperty("图片选择")
	private Integer picSelection;

    /**
     * 是否被选
     */
	@Excel(name = "是否被选")
	@ApiModelProperty("是否被选")
	private Integer picBool;


}
