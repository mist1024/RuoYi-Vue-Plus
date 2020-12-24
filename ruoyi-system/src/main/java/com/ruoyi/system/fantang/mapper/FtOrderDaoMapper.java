package com.ruoyi.system.fantang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.fantang.domain.FtOrderDao;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * 订单管理Mapper接口
 *
 * @author ft
 * @date 2020-11-19
 */
public interface FtOrderDaoMapper extends BaseMapper<FtOrderDao> {

    @Insert("insert into ft_order (order_type, staff_id, order_src, create_at, order_date, order_list, total_price) select type as order_type, staff_id, 1 as order_src, now() as create_at, date_add(now(), interval 1 day) as order_date, foods, (select sum(price) from ft_food f where FIND_IN_SET(f.food_id,d.foods)) as price from ft_staff_demand d where d.demand_mode = 1")
    void GenerateStaffTomorrowOrder();

    @Select("select order_type, count(*)  as count_order from ft_order where order_date BETWEEN #{start} and #{end} GROUP BY order_type")
    List<FtOrderDao> statisGetOrderOfDate(@Param("start")String start,@Param("end") String end);
}
