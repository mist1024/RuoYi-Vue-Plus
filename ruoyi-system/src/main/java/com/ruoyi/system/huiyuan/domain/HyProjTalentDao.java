package com.ruoyi.system.huiyuan.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 优才项目对象 hy_proj_talent
 *
 * @author ryo
 * @date 2021-01-09
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("hy_proj_talent")
public class HyProjTalentDao implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 驿站分类
     */
    @Excel(name = "驿站分类")
    private Long postType;

    /**
     * 店铺清单
     */
    @Excel(name = "店铺清单")
    private String shopList;

    /**
     * 首页 URL
     */
    @Excel(name = "首页 URL")
    private String homeUrl;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    /**
     * 更新时间
     */
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateAt;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();

    /**
     * 项目 id
     */
    private Long projId;
}
