package com.ruoyi.system.fantang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.fantang.domain.FtSettleDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 结算报Mapper接口
 *
 * @author ft
 * @date 2020-11-19
 */
public interface FtSettleDaoMapper extends BaseMapper<FtSettleDao> {

    @Update("UPDATE ft_settle a set list = (select GROUP_CONCAT(b.id) from ft_report_meals b where b.patient_id =  #{patientId} and b.create_at BETWEEN #{lastBillingDate} and #{selectBillingDate}) where a.settle_id = #{settlementId} ")
    Integer updateList(@Param("settlementId") Long settlementId, @Param("patientId") Long patientId, @Param("lastBillingDate") String lastBillingDate, @Param("selectBillingDate") String selectBillingDate);
}
