package com.ruoyi.isc.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.domain.entity.SysMenu;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

/**
 * 服务分类对象 isc_service_cate
 *
 * @author wenchao gong
 * @date 2021-08-22
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("isc_service_cate")
public class IscServiceCate implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * 分类ID
     */
    @TableId(value = "cate_id")
    private Long cateId;

    /**
     * 父分类ID
     */
    private Long parentId;

    /**
     * 分类名称
     */
    private String cateName;

    /**
     * 搜索全路径
     */
    private String fullPath;

    /**
     * 服务状态（0启用 1停用）
     */
    private String enabled;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 显示顺序
     */
    private Long orderNum;

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

    /**
     * 子分类
     */
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<SysMenu>();

}
