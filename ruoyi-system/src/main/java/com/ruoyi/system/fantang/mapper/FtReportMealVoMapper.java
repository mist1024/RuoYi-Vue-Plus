package com.ruoyi.system.fantang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.fantang.domain.FtReportMealsDao;
import com.ruoyi.system.fantang.vo.FtReportMealVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 报餐管理Mapper接口
 *
 * @author ft
 * @date 2020-11-19
 */
@Repository
public interface FtReportMealVoMapper extends BaseMapper<FtReportMealVo> {
    @Select("select a.*, b.hospital_id, b.bed_id, b.`name`, c.depart_name, c.depart_code from ft_report_meals a LEFT JOIN ft_patient b on a.patient_id = b.patient_id LEFT JOIN ft_depart c on b.depart_id = c.depart_id")
    public List<FtReportMealVo> listAll();

    @Select("select a.*, b.hospital_id, b.bed_id, b.`name`, c.depart_name, c.depart_code from ft_report_meals a LEFT JOIN ft_patient b on a.patient_id = b.patient_id LEFT JOIN ft_depart c on b.depart_id = c.depart_id where a.settlement_flag = 0")
    public List<FtReportMealVo> listNoPay();

    @Select("select a.*, b.hospital_id, b.bed_id, b.`name`, c.depart_name, c.depart_code from ft_report_meals a LEFT JOIN ft_patient b on a.patient_id = b.patient_id LEFT JOIN ft_depart c on b.depart_id = c.depart_id  where a.settlement_flag = 1")
    public List<FtReportMealVo> listPayoff();
}
