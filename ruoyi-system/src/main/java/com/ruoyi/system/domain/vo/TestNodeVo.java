package com.ruoyi.system.domain.vo;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 节点维护视图对象 test_node
 *
 * @author qianlan
 * @date 2021-08-08
 */
@Data
@ApiModel("节点维护视图对象")
public class TestNodeVo {

	private static final long serialVersionUID = 1L;

	/**
     *  主键
     */
	@ApiModelProperty("主键")
	private Long id;

    /**
     * 节点名
     */
	@Excel(name = "节点名")
	@ApiModelProperty("节点名")
	private String name;

    /**
     * 分类
     */
	@Excel(name = "分类")
	@ApiModelProperty("分类")
	private String categary;

    /**
     * 父id
     */
	@Excel(name = "父id")
	@ApiModelProperty("父id")
	private Long pid;


}
