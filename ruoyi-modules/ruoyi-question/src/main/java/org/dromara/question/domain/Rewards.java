package org.dromara.question.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 奖品管理对象 f_rewards
 *
 * @author lvxudong
 * @date 2023-11-27
 */
@Data
@TableName("f_rewards")
public class Rewards implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 奖励类型
     */
    private Integer type;

    /**
     * 奖励名称
     */
    private String name;

    /**
     * 奖励图片
     */
    private String image;

    /**
     * 图片描述
     */
    private String imgDescribe;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
