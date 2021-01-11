package com.ruoyi.system.fantang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.fantang.domain.FtFoodDemandDao;
import com.ruoyi.system.fantang.domain.FtOrderDao;
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
    @Insert("insert into ft_food_demand (patient_id, foods, type) select #{patient_id}, food_list, type FROM ft_food_default")
    public Integer GenerateOrderByPatientId(@Param("patient_id") Long patientId);

    @Select("select a.patient_id  from ft_patient a where a.patient_id not in (select patient_id from ft_food_demand c )")
    public List<Long> getNewPatientNotDemand();

    List<FtFoodDemandDao> listNewFormatter(FtFoodDemandDao ftFoodDemandDao);

    FtFoodDemandDao getByIdNewFormatter(Long id);

    @Select("SELECT\n" +
            "\td.depart_name,\n" +
            "\tcount(*) AS total,\n" +
            "\ta.type \n" +
            "FROM\n" +
            "\tft_report_meals a\n" +
            "\tLEFT JOIN ft_food_demand b ON a.patient_id = b.patient_id\n" +
            "\tLEFT JOIN ft_patient c ON a.patient_id = c.patient_id\n" +
            "\tLEFT JOIN ft_depart d ON c.depart_id = d.depart_id \n" +
            "WHERE\n" +
            "\tb.flag = 1 \n" +
            "\tAND a.type = b.type \n" +
            "\tAND c.depart_id = d.depart_id  and  a.create_at BETWEEN #{start} and #{end}\n" +
            "\t\n" +
            "GROUP BY\n" +
            "\td.depart_name,\n" +
            "\ta.type")
    List<FtOrderDao> getStatisticsOfDate(@Param("start") String start, @Param("end") String end);
}
