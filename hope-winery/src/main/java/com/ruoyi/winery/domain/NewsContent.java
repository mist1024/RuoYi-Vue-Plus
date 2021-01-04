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
 * 新闻资讯对象 news_content
 * 
 * @author ruoyi
 * @date 2020-12-31
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("news_content")
public class NewsContent implements Serializable {

private static final long serialVersionUID=1L;


    /** 规格ID */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    private Date updateTime;

    /** 新闻标题 */
    @Excel(name = "新闻标题")
    private String newsTitle;

    /** 新闻详情 */
    @Excel(name = "新闻详情")
    private String newsBody;

    /** 新闻封面图 */
    @Excel(name = "新闻封面图")
    private String newsImage;

    /** 新闻类型 */
    @Excel(name = "新闻类型")
    private Integer newsType;

    /** 状态 */
    @Excel(name = "状态")
    private Integer state;
}
