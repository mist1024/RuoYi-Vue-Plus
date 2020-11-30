package com.ruoyi.system.fantang.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.system.fantang.domain.FtReportMealsDao;
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
public class FtReportMealVo extends FtReportMealsDao {

//    private static final long serialVersionUID = 1L;

    /**
     * select a.*, b.hospital_id, b.bed_id, b.`name`, c.depart_name
     * from ft_report_meals a LEFT JOIN ft_patient b on a.patient_id = b.patient_id
     * LEFT JOIN ft_depart c on b.depart_id = c.depart_id
     */
    private String name;

    private String bedId;

    private String departName;

    private String hospitalId;

    private String departCode;

//    private Date createAt;
//
//    private Integer type;
//
//    private Long patientId;
//
//    private String createBy;
//
//    private String foods;
//
//    private float price;
//
//    private Integer settlementFlag;
//

}
