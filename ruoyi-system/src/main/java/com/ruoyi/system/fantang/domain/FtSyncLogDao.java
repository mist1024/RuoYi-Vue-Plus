package com.ruoyi.system.fantang.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 科室管理对象 ft_depart
 * 
 * @author ft
 * @date 2020-11-24
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_sync_log")
public class FtSyncLogDao implements Serializable {

private static final long serialVersionUID=1L;


    /** 同步时间 */
    @TableField(value = "created_at")
    private Date createAt;

    @TableField("total_record")
    private Long totalRecord;

    @TableField("sync_record")
    private Long SyncRecord;

}
