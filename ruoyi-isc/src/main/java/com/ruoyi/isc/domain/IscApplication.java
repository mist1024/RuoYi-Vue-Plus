package com.ruoyi.isc.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 应用信息对象 isc_application
 *
 * @author wenchao gong
 * @date 2021-09-08
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("isc_application")
public class IscApplication implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * 应用ID
     */
    @TableId(value = "application_id")
    private Long applicationId;

    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * 应用密钥
     */
    private String accessKey;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

    /**
     * 备注
     */
    private String remark;

}
