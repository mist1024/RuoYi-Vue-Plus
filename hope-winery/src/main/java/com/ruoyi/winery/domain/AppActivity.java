package com.ruoyi.winery.domain;

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
 * 活动对象 app_activity
 * 
 * @author ruoyi
 * @date 2021-01-20
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_activity")
public class AppActivity implements Serializable {

private static final long serialVersionUID=1L;


    /** ID */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /** 链接 */
    @Excel(name = "链接")
    private String url;

    /** 1每日精选2热门活动 */
    @Excel(name = "1每日精选2热门活动")
    private Integer type;

    /** 图片 */
    @Excel(name = "图片")
    private String image;

    /** 高度 */
    @Excel(name = "高度")
    private Integer imageHeight;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    private Date updateTime;
}
