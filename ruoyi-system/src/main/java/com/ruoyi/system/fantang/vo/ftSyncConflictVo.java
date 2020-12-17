package com.ruoyi.system.fantang.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.fantang.domain.FtSyncPatientDao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 远程病患数据冲突数据实体类
 * 
 * @author 陈智兴
 * @date 2020-12-17
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class ftSyncConflictVo extends FtSyncPatientDao implements Serializable {

private static final long serialVersionUID=1L;



    /** 本地住院号 */
    @TableField(value = "old_hospital_id")
    private String oldHospitalid;

    /** 本地姓名 */
    @TableField(value = "old_name")
    private String oldName;

    // 本地科室名称
    @TableField("old_depart_name")
    private String oldDepartName;

    // 本地床号
    @TableField("old_bed_id")
    private String oldDedId;

    @TableField("depart_id")
    private String departId;

    @TableField("old_depart_id")
    private String oldDepartId;
}
