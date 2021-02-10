package com.ruoyi.system.fantang.domain;

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
import java.math.BigDecimal;
import java.util.Date;

/**
 * 收费管理对象 ft_prepayment
 *
 * @author ft
 * @date 2020-11-19
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class FtPrepaymentVo extends FtPrepaymentDao {

    /**
     * select a.patient_id , a.name,  a.hospital_id, b.depart_name, b.depart_code from ft_patient a
     * LEFT JOIN ft_depart b on a.depart_id = b.depart_id
     * where a.patient_id not in (select patient_id from ft_prepayment )
     */
    private String name;

    private String bedId;

    private String departName;

    private String hospitalId;

    private String departCode;
}
