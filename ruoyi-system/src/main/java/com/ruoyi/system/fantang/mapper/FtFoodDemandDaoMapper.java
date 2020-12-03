package com.ruoyi.system.fantang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.fantang.domain.FtFoodDemandDao;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 病人报餐Mapper接口
 *
 * @author ft
 * @date 2020-12-03
 */
public interface FtFoodDemandDaoMapper extends BaseMapper<FtFoodDemandDao> {
    @Insert("insert into ft_food_demand (patient_id, foods, type, price) select #{patient_id}, food_list, type, price FROM ft_food_default")
    public Integer GenerateOrderByPatientId(@Param("patient_id") Long patientId);

    @Select("select a.patient_id  from ft_patient a where a.patient_id not in (select patient_id from ft_food_demand c )")
    public List<Long> getNewPatientNotDemand();
}
