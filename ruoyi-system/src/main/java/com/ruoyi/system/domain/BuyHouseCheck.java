package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 购房审核人员不同类型需要的审核人员名单对象 buy_house_check
 *
 * @author ruoyi
 * @date 2023-02-24
 */
@Data
@TableName("buy_house_check")
public class BuyHouseCheck{

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 描述
     */
    private String remark;

    /**
     * 人员
     */
    private String person;
    /**
     * 需要关联的key
     */
    private String otherKey;
    /**
     * 关键key
     */
    private String cruxKey;

    private String step;

}
