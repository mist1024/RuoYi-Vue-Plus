package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

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
    @TableId(value = "id")
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

}
