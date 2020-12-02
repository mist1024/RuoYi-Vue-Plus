package com.ruoyi.system.fantang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.fantang.domain.FtReportMealsDao;
import com.ruoyi.system.fantang.entity.ReportMealsDayEntity;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * 报餐管理Mapper接口
 *
 * @author ft
 * @date 2020-11-19
 */
public interface FtReportMealsDaoMapper extends BaseMapper<FtReportMealsDao> {

    @Select("SELECT sum(price) as price  from ft_report_meals where patient_id = #{patientId}  and settlement_flag = 0 and create_at BETWEEN #{createAt} and #{selectBillingDate}")
    Long countBillingBetween(Long patientId, Date createAt, Date selectBillingDate);
}
