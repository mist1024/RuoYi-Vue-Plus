package com.ruoyi.system.fantang.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.system.fantang.entity.BasePatient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 病人报餐对象 ft_food_demand
 *
 * @author ft
 * @date 2020-12-03
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_food_demand")
public class FtFoodDemandDao  extends BasePatient implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 病人id
     */
    private Long patientId;

    /**
     * 正餐清单
     */
    @Excel(name = "正餐清单")
    private String foods;

    /**
     * 正餐类型
     */
    @Excel(name = "正餐类型")
    private Long type;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 更新日期
     */
    @Excel(name = "更新日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateAt;

    /**
     * 加菜
     */
    @Excel(name = "加菜")
    private Boolean vegetables;

    /**
     * 更新操作人 id
     */
    private Long updateBy;

    /**
     * 加肉
     */
    @Excel(name = "加肉")
    private Boolean meat;

    /**
     * 更新来源
     */
    private Integer updateFrom;

    /**
     * 加饭
     */
    @Excel(name = "加饭")
    private Boolean rice;

    /**
     * 加蛋
     */
    @Excel(name = "加蛋")
    private Integer egg;

    /**
     * 订单详情
     */
    private String orderInfo;

    /**
     * 启用状态
     */
    @Excel(name = "启用状态")
    private Boolean flag;
}
