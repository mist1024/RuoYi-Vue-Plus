package com.ruoyi.demo.domain.vo;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 图片选择参数视图对象 hjc_selection
 *
 * @author qianlan
 * @date 2021-10-12
 */
@Data
@ApiModel("图片选择参数视图对象")
public class HjcSelectionVo {

	private static final long serialVersionUID = 1L;

	/**
     *  主键
     */
	@ApiModelProperty("主键")
	private Long sid;

    /**
     * hjc_pic表的主键
     */
	@Excel(name = "hjc_pic表的主键")
	@ApiModelProperty("hjc_pic表的主键")
	private Long pid;

    /**
     * 选择
     */
	@Excel(name = "选择")
	@ApiModelProperty("选择")
	private Integer selection;


}
