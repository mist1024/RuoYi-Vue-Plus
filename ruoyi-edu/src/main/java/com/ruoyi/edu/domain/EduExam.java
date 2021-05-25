package com.ruoyi.edu.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;

/**
 * 考试信息对象 edu_exam
 *
 * @author keyleaf
 * @date 2021-05-23
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("edu_exam")
public class EduExam implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id")
    private Long id;

    /** 考试编号 */
    private String examNo;

    /** 考试名称 */
    private String examName;

    /** 考试简称 */
    private String examShortName;

    /** 考试日期 */
    private Date examDate;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人id */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /** 修改时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /** 修改人id */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

}
