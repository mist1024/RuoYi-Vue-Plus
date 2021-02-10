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
 * 饭堂参数对象 ft_config
 * 
 * @author ft
 * @date 2020-12-07
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_config")
public class FtConfigDao implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id")
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    private Long corpId;

    /** $column.columnComment */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    private String configKey;

    /** $column.columnComment */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    private String configValue;

    /** $column.columnComment */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    private Long flag;
}
