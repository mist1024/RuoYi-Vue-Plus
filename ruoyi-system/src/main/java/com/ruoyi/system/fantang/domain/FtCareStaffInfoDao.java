package com.ruoyi.system.fantang.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 护工信息对象 ft_care_staff_info
 *
 * @author ft
 * @date 2020-11-19
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_care_staff_info")
public class FtCareStaffInfoDao implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 护工 id
     */
    @TableId(value = "care_staff_id")
    private Long careStaffId;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String name;

    /**
     * 所属公司名称
     */
    @Excel(name = "所属公司名称")
    private String corpName;

    /**
     * 所属科室清单
     */
    @Excel(name = "所属科室清单")
    private String departList;

    /**
     * 启用标志
     */
    private Integer flag;

    /**
     * 照片
     */
    @Excel(name = "照片")
    private String photo;

    /**
     * 二维码
     */
    @Excel(name = "二维码")
    private String qrCode;
}
