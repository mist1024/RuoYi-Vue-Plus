package com.ruoyi.system.fantang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.fantang.domain.FtOrderDao;
import org.apache.ibatis.annotations.Insert;

/**
 * 订单管理Mapper接口
 *
 * @author ft
 * @date 2020-11-19
 */
public interface FtOrderDaoMapper extends BaseMapper<FtOrderDao> {

    @Insert("insert into ft_order (order_type, staff_id, order_src, create_at, order_date) select type as order_type, b.staff_id, 1 as order_src, now() as create_at, date_add(now(), interval 1 day) as order_date from ft_staff_demand b where b.demand_mode = 1")
    void GenerateStaffTomorrowOrder();
}
