package com.ruoyi.system.fantang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.fantang.domain.FtOrderDao;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    @Select("select a.order_type, count(a.order_type) as count_order , c.depart_name   from ft_order a\n" +
            "    LEFT JOIN ft_staff_info b on a.staff_id = b.staff_id\n" +
            "    LEFT JOIN ft_depart c on b.depart_id = c.depart_id where b.depart_id = c.depart_id and a.order_date BETWEEN #{start} and #{end}\n" +
            "    GROUP BY a.order_type, c.depart_name")
    List<FtOrderDao> statisGetOrderOfDate(@Param("start") String start, @Param("end") String end);

    @Select("SELECT\n" +
            "\ta.order_type,\n" +
            "\tcount( a.order_type ) AS count_order,\n" +
            "\tc.depart_name,\n" +
            "\tb.`name` AS staff_name,\n" +
            "\tb.tel \n" +
            "FROM\n" +
            "\tft_order a\n" +
            "\tLEFT JOIN ft_staff_info b ON a.staff_id = b.staff_id\n" +
            "\tLEFT JOIN ft_depart c ON b.depart_id = c.depart_id \n" +
            "WHERE\n" +
            "\tb.depart_id = c.depart_id \n" +
            "\tAND a.order_date BETWEEN #{start} \n" +
            "\tAND #{end} \n" +
            "GROUP BY\n" +
            "\ta.order_type,\n" +
            "\tc.depart_name,\n" +
            "\tb.`name`,\n" +
            "\tb.tel")
    List<FtOrderDao> statisGetOrderOfDateByPerson(@Param("start") String start, @Param("end") String end);

    List<FtOrderDao> listDetailedByDate(@Param("orderType") Integer orderType, @Param("start") String start, @Param("end") String end);

    List<FtOrderDao> listAllDetailedByDate(String start, String end);
}
