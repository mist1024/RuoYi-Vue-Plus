package com.ruoyi.work.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 撤销记录对象 roll_back_log
 *
 * @author ruoyi
 * @date 2023-03-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("roll_back_log")
public class RollBackLog extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 是后台撤回还是前台撤回
     */
    private String type;
    /**
     * 撤销原因
     */
    private String reply;

    /**
     * 将运行表中的数据保存
     */
    private String param;

    @TableField(exist = false)
    private String businessId;

    @TableField(exist = false)
    private String step;

    @TableField(exist = false)
    private String checked;

    @TableField(exist = false)
    private String checkType;

    @TableField(exist = false)
    private String audit;

    @TableField(exist = false)
    private String processKey;

}
