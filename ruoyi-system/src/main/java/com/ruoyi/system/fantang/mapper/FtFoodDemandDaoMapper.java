package com.ruoyi.system.fantang.mapper;

import com.ruoyi.system.fantang.domain.FtFoodDemandDao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * 病人报餐Mapper接口
 *
 * @author ft
 * @date 2020-11-26
 */
public interface FtFoodDemandDaoMapper extends BaseMapper<FtFoodDemandDao> {
    @Insert("insert into ft_food_demand (patient_id, foods, type) select #{patient_id}, food_list, type FROM ft_food_default")
    public Integer GenerateOrderByPatientId(@Param("patient_id") Long patientId) ;

}
