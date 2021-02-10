package com.ruoyi.system.fantang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.fantang.domain.FtFoodDemandDao;
import com.ruoyi.system.fantang.domain.FtOrderDao;
import com.ruoyi.system.fantang.domain.FtStaffDemandDao;
import com.ruoyi.system.fantang.domain.FtStaffStopMealsDao;
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
    IPage<FtOrderDao>  statisGetOrderOfDate(Page<FtOrderDao> page, @Param("start") String start, @Param("end") String end);

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
    IPage<FtOrderDao> statisGetOrderOfDateByPerson(Page<FtOrderDao> page, @Param("start") String start, @Param("end") String end);

    List<FtOrderDao> listDetailedByDate(@Param("orderType") Integer orderType, @Param("start") String start, @Param("end") String end);

    List<FtOrderDao> listAllDetailedByDate(String start, String end);


    @Select("SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\tft_order \n" +
            "WHERE\n" +
            "\tstaff_id = #{staffId} \n" +
            "\tAND order_type = #{type} \n" +
            "\tAND order_date BETWEEN DATE(\n" +
            "\tNOW()) \n" +
            "\tAND date_add(\n" +
            "\tnow(),\n" +
            "\tINTERVAL 1 DAY)")
    FtOrderDao getNowOrder(@Param("staffId") Long staffId, @Param("type") int type);


    @Insert("INSERT INTO ft_order ( order_type, staff_id, order_list, total_price, create_at, pay_type, pay_flag, write_off_flag, write_off_at, device_id, order_date ) SELECT\n" +
            "#{type},\n" +
            "#{staffId},\n" +
            "b.food_list,\n" +
            "b.price,\n" +
            "now(),\n" +
            "1,\n" +
            "1,\n" +
            "1,\n" +
            "NOW(),\n" +
            "#{deviceId},\n" +
            "NOW() \n" +
            "FROM\n" +
            "\tft_food_default b \n" +
            "WHERE\n" +
            "\tb.type = #{type}")
    void insertOrderAndWriteOff(@Param("staffId") Long staffId, @Param("type") int type, @Param("deviceId") Long deviceId);

    @Select("SELECT\n" +
            "\tc.depart_name,\n" +
            "\ta.type,\n" +
            "\ta.flag,\n" +
            "count(*) as total\n" +
            "FROM\n" +
            "\tft_food_demand a\n" +
            "LEFT JOIN ft_patient b ON a.patient_id = b.patient_id\n" +
            "LEFT JOIN ft_depart c ON b.depart_id = c.depart_id\n" +
            "WHERE\n" +
            "\tb.off_flag = 0\n" +
            "AND a.flag = 1\n" +
            "GROUP BY a.type, c.depart_name")
    List<FtFoodDemandDao> getStatisticsReportMealsOfTomorrow();

    @Select("SELECT\n" +
            "\tc.depart_name,\n" +
            "\ta.type,\n" +
            "\tcount(*) as total\n" +
            "FROM\n" +
            "\tft_staff_demand a\n" +
            "LEFT JOIN ft_staff_info b ON a.staff_id = b.staff_id\n" +
            "LEFT JOIN ft_depart c ON b.depart_id = c.depart_id\n" +
            "WHERE\n" +
            "\ta.demand_mode = 1\n" +
            "GROUP BY\n" +
            "\tc.depart_name,\n" +
            "\ta.type")
    List<FtStaffDemandDao> getStatisticsStaffOfTomorrow();

    @Select("SELECT\n" +
            "\tc.depart_name,\n" +
            "\ta.type,\n" +
            "\tcount(*) as total\n" +
            "FROM\n" +
            "\tft_staff_stop_meals a\n" +
            "LEFT JOIN ft_staff_info b ON a.staff_id = b.staff_id\n" +
            "LEFT JOIN ft_depart c ON b.depart_id = c.depart_id\n" +
            "WHERE\n" +
            "demand_date BETWEEN date_add(NOW(),  INTERVAL 1 DAY)\n" +
            "AND date_add(now(), INTERVAL 2 DAY)\n" +
            "GROUP BY\n" +
            "\tc.depart_name,\n" +
            "\ta.type\n")
    List<FtStaffStopMealsDao> getStopOrderOfTomorrow();

    @Select("SELECT\n" +
            "\tc.depart_name,\n" +
            "\tb.`name`, b.tel,\n" +
            "\ta.* \n" +
            "FROM\n" +
            "\tft_order a\n" +
            "\tLEFT JOIN ft_staff_info b ON b.staff_id = a.staff_id\n" +
            "\tLEFT JOIN ft_depart c ON c.depart_id = b.depart_id \n" +
            "WHERE\n" +
            "\ta.order_date BETWEEN #{start} \n" +
            "\tAND #{end}")
    List<FtOrderDao> statisOrderOfDateByPersonNoPage( @Param("start") String start, @Param("end") String end);
}
