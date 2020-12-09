package com.ruoyi.system.fantang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.fantang.domain.FtPrepaymentDao;
import com.ruoyi.system.fantang.domain.FtPrepaymentVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 收费管理Mapper接口
 *
 * @author ft
 * @date 2020-11-19
 */
public interface FtPrepaymentDaoMapper extends BaseMapper<FtPrepaymentDao> {

    @Select("select a.patient_id , a.name,  a.hospital_id, a.bed_id, b.depart_name, b.depart_code  from ft_patient a LEFT JOIN ft_depart b on a.depart_id = b.depart_id where a.patient_id not in (select patient_id from ft_prepayment )")
    List<FtPrepaymentVo> listNoPrepay();

    @Select("SELECT a.*,b.hospital_id, b.name, b.bed_id, c.depart_name from ft_prepayment a LEFT JOIN ft_patient b on a.patient_id = b.patient_id LEFT JOIN ft_depart c on b.depart_id = c.depart_id where a.settlement_flag = 0")
    List<FtPrepaymentVo> listPrepay();

    @Select("SELECT a.*,b.hospital_id, b.name, b.bed_id, c.depart_name from ft_prepayment a LEFT JOIN ft_patient b on a.patient_id = b.patient_id LEFT JOIN ft_depart c on b.depart_id = c.depart_id where a.settlement_flag = 1")
    List<FtPrepaymentVo> listAllPrepay();
}
