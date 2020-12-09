package com.ruoyi.system.fantang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.fantang.domain.FtReportMealsDao;
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
}
