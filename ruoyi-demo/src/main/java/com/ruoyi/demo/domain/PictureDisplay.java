package com.ruoyi.demo.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 图片展示对象 hjc_pic
 *
 * @author qianlan
 * @date 2021-10-10
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("hjc_pic")
public class PictureDisplay implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * 主键
     */
    @TableId(value = "pic_id")
    private Long picId;

    /**
     * url地址
     */
    private String picUrl;

    /**
     * 图片选择
     */
    private Integer picSelection;

    /**
     * 是否被选
     */
    private Integer picBool;

}
