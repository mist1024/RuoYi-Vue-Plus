package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 预约导出对象 subscribe_export
 *
 * @author ruoyi
 * @date 2023-04-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("subscribe_export")
public class SubscribeExport extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 路径
     */
    private String path;
    /**
     * 申请人id
     */
    private String userId;

    private String description;

    private String processKey;

    private String exportStatus;

}
