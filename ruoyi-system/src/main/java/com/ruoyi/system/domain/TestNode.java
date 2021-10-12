package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

/**
 * 节点维护对象 test_node
 *
 * @author qianlan
 * @date 2021-08-08
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("test_node")
public class TestNode implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.INPUT)
    private Long id;

    /**
     * 节点名
     */
    private String name;

    /**
     * 分类
     */
    private String categary;

    /**
     * 父id
     */
    private Long pid;

	/**
	 * 子节点
	 */
	@TableField(exist = false)
	private List<TestNode> children;
}
