package com.ruoyi.system.fantang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.fantang.domain.FtFoodDemandDao;
import com.ruoyi.system.fantang.domain.FtOrderDao;
import com.ruoyi.system.fantang.domain.FtReportMealsDao;
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
    @Insert("insert into ft_food_demand (patient_id, foods, type, flag,create_at ) select #{patient_id}, food_list, type, 1, now() FROM ft_food_default")
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
            "\tLEFT JOIN ft_patient c ON a.patient_id = c.patient_id\n" +
            "\tLEFT JOIN ft_depart d ON c.depart_id = d.depart_id \n" +
            "WHERE\n" +
            "\tc.depart_id = d.depart_id  and  a.create_at BETWEEN #{start} and #{end}\n" +
            "\t\n" +
            "GROUP BY\n" +
            "\td.depart_name,\n" +
            "\ta.type")
    List<FtOrderDao> getStatisticsOfDate(@Param("start") String start, @Param("end") String end);

    @Select("SELECT\n" +
            "\ta.type,\n" +
            "\tc.depart_name,\n" +
            "\tCOUNT(*) AS total \n" +
            "FROM\n" +
            "\tft_food_demand a\n" +
            "\tLEFT JOIN ft_patient b ON b.patient_id = a.patient_id\n" +
            "\tLEFT JOIN ft_depart c ON c.depart_id = b.depart_id \n" +
            "WHERE\n" +
            "\tb.depart_id = c.depart_id \n" +
            "\tAND flag = 1 \n" +
            "GROUP BY\n" +
            "\ta.type,\n" +
            "\tc.depart_name")
    List<FtReportMealsDao> getStatisticsFoodDemand();
}
