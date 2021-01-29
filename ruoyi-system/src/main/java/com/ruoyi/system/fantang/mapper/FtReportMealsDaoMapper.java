package com.ruoyi.system.fantang.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.fantang.domain.FtReportMealsDao;
import com.ruoyi.system.fantang.entity.ReportMealsPriceEntity;
import com.ruoyi.system.fantang.vo.FtReportMealVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * 报餐管理Mapper接口
 *
 * @author ft
 * @date 2020-11-19
 */
public interface FtReportMealsDaoMapper extends BaseMapper<FtReportMealsDao> {

    @Select("SELECT sum(price) as price  from ft_report_meals where patient_id = #{patientId}  and settlement_flag = 0 and create_at BETWEEN #{createAt} and #{selectBillingDate}")
    Long countBillingBetween(Long patientId, Date createAt, Date selectBillingDate);

    List<FtReportMealsDao> listMealsWithInSettle(FtReportMealsDao ftReportMealsDao);

    @Update("UPDATE ft_report_meals set settlement_flag = 1 , settlement_at = now() , settlement_id = #{settlementId} where patient_id =  #{patientId} and create_at BETWEEN #{lastBillingDate} and #{selectBillingDate}")
    Integer settleMeals(@Param("settlementId") Long settlementId, @Param("patientId") Long patientId, @Param("lastBillingDate") String lastBillingDate, @Param("selectBillingDate") String selectBillingDate);


    /**
     * SELECT a.patient_id,sum(a.price) as price , sum(a.nutrition_food_price ) as nutrition_food_price , sum(a.total_price) as total_price   FROM ft_report_meals a where a.patient_id = 2 and a.create_at BETWEEN '2020-12-01' AND '2020-12-22'
     *
     * @param patientId
     * @param lastBillingDate
     * @param selectBillingDate
     * @return
     */
    @Select("SELECT a.patient_id,sum(a.price) as dinner_total_price , sum(a.nutrition_food_price ) as nutrition_total_price , sum(a.total_price) as sum_total_price FROM ft_report_meals a where a.patient_id = #{patientId} and a.dining_at BETWEEN #{lastBillingDate} AND #{selectBillingDate}")
    ReportMealsPriceEntity sumTotalPrice(@Param("patientId") Long patientId, @Param("lastBillingDate") Date lastBillingDate, @Param("selectBillingDate") Date selectBillingDate);

    List<FtReportMealsDao> listNutrition(@Param("beginOfDay") DateTime beginOfDay, @Param("endOfDay") DateTime endOfDay, FtReportMealsDao ftReportMealsDao);

    List<FtReportMealsDao> listAllNutrition(FtReportMealsDao ftReportMealsDao);

    @Select("SELECT a.type, a.foods,  count(a.foods) as count, b.depart_id,c.depart_name\n" +
            "from ft_report_meals a \n" +
            "LEFT JOIN ft_patient b on a.patient_id  = b.patient_id\n" +
            "LEFT JOIN ft_depart c on b.depart_id =  c.depart_id \n" +
            "where  b.depart_id = #{departId}  and a.dining_at BETWEEN #{beginOfDay} and #{endOfDay}\n" +
            "GROUP BY a.type, foods")
    List<FtReportMealVo> getStatisticsFoods(@Param("departId") Integer departId,@Param("beginOfDay") DateTime beginOfDay, @Param("endOfDay") DateTime endOfDay);

    @Select("SELECT a.patient_id,sum(a.price) as dinner_total_price , sum(a.nutrition_food_price ) as nutrition_total_price , sum(a.total_price) as sum_total_price FROM ft_report_meals a where a.patient_id = #{patientId} AND a.settlement_flag = 0 AND a.dining_flag = 1")
    ReportMealsPriceEntity sumAllTotalPrice(@Param("patientId") Long patientId);

    // SELECT a.*, b.`name` , b.bed_id, b.hospital_id, c.depart_name from ft_report_meals a LEFT JOIN ft_patient b on a.patient_id = b.patient_id LEFT JOIN ft_depart c on b.depart_id  =c.depart_id where a.patient_id in  (7) and a.create_at = CURDATE() + 1
    List<FtReportMealsDao> listPatientReportMeals(FtReportMealsDao ftReportMealsDao);
}
