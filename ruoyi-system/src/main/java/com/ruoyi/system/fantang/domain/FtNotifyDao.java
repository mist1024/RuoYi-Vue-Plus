package com.ruoyi.system.fantang.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.ruoyi.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 系统信息对象 ft_notify
 * 
 * @author ft
 * @date 2020-12-17
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_notify")
public class FtNotifyDao implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id")
    private Long id;

    /** 消息类型 */
    @Excel(name = "消息类型")
    private Integer messageType;

    /** 消息范围 */
    @Excel(name = "消息范围")
    private Integer scope;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String messageBody;

    /** 创建时间 */
    @Excel(name = "创建时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    /** 创建人 */
    private String createBy;

    /** 阅读标志 */
    @Excel(name = "阅读标志")
    private Integer readFlag;
}
