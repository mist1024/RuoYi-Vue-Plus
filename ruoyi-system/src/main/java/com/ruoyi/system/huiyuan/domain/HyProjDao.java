package com.ruoyi.system.huiyuan.domain;

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
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;

/**
 * 项目信息对象 hy_proj
 * 
 * @author ryo
 * @date 2021-01-09
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("hy_proj")
public class HyProjDao implements Serializable {

private static final long serialVersionUID=1L;


    /** id */
    @TableId(value = "id")
    private Long id;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projName;

    /** 首页 URL */
    @Excel(name = "首页 URL")
    private String homeUrl;

    /** 创建时间 */
    @Excel(name = "创建时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    /** 更新时间 */
    @Excel(name = "更新时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateAt;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
