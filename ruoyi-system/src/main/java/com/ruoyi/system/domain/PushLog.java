package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 推送日志对象 push_log
 *
 * @author ruoyi
 * @date 2023-07-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("push_log")
public class PushLog extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 推送数据
     */
    private String pushData;
    /**
     * 返回结果
     */
    private String resultData;

}
