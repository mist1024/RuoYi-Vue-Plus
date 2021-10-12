package com.ruoyi.demo.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 图片选择参数对象 hjc_selection
 *
 * @author qianlan
 * @date 2021-10-12
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("hjc_selection")
public class HjcSelection implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * 主键
     */
    @TableId(value = "sid")
    private Long sid;

    /**
     * hjc_pic表的主键
     */
    private Long pid;

    /**
     * 选择
     */
    private Integer selection;

}
