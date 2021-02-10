package com.ruoyi.system.fantang.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.system.fantang.vo.FtCateringVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * 配餐功能对象 ft_catering
 *
 * @author ft
 * @date 2020-12-07
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_catering")
public class FtCateringDao extends FtCateringVo {

    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 病人 id
     */
    private Long patientId;

    /**
     * 病人 ids
     */
    @TableField(exist = false)
    private List<Long> patientIds;

    /**
     * 正餐类型
     */
    @Excel(name = "正餐类型")
    private Integer type;

    /**
     * 配餐号
     */
    @Excel(name = "配餐号")
    private Long number;

    /**
     * 配餐频次
     */
    @Excel(name = "配餐频次")
    private String frequency;

    /**
     * 用法
     */
    @Excel(name = "用法")
    private Integer cateringUsage;

    /**
     * 是否代替正餐
     */
    private Integer isReplace;

    /**
     * 启用标志
     */
    private Boolean flag;

    /**
     * 更新日期
     */
    private Date updateAt;

    /**
     * 更新人
     */
    @Excel(name = "更新人")
    private String updateBy;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String cateringDescribe;

    // 暂停标志
    @Excel(name = "停餐")
    private Boolean suspendFlag;
}
