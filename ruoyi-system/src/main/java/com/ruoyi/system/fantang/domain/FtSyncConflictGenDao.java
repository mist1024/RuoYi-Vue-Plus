package com.ruoyi.system.fantang.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 同步冲突对象 ft_sync_conflict
 *
 * @author ft
 * @date 2020-12-24
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_sync_conflict")
public class FtSyncConflictGenDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    private String hospitalId;

    private String name;

    private String departName;

    private String bedId;

    private String oldHospitalId;

    private String oldName;

    private String oldDepartName;

    private String oldBedId;

    private Long departId;

    private Long oldDepartId;

    @TableField(exist = false)
    private Integer patientFlag;

    private Long patientId;

    private Integer isSolve;
}
