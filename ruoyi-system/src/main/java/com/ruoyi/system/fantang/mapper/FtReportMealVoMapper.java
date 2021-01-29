package com.ruoyi.system.fantang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.fantang.domain.FtReportMealsDao;
import com.ruoyi.system.fantang.vo.FtReportMealVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 报餐管理Mapper接口
 *
 * @author ft
 * @date 2020-11-19
 */
@Repository
public interface FtReportMealVoMapper extends BaseMapper<FtReportMealVo> {
    @Select("select a.price, a.patient_id, b.hospital_id, b.bed_id, b.`name`, c.depart_name, c.depart_code from ft_report_meals a LEFT JOIN ft_patient b on a.patient_id = b.patient_id LEFT JOIN ft_depart c on b.depart_id = c.depart_id")
    public List<FtReportMealVo> listAll();

    @Select("select sum(a.price) as price, a.patient_id, b.hospital_id, b.bed_id, b.`name`, c.depart_name, c.depart_code from ft_report_meals a LEFT JOIN ft_patient b on a.patient_id = b.patient_id LEFT JOIN ft_depart c on b.depart_id = c.depart_id where a.settlement_flag = 0 GROUP BY a.patient_id")
    public List<FtReportMealVo> listNoPay();

    @Select("select sum(a.price) as price, a.patient_id, b.hospital_id, b.bed_id, b.`name`, c.depart_name, c.depart_code from ft_report_meals a LEFT JOIN ft_patient b on a.patient_id = b.patient_id LEFT JOIN ft_depart c on b.depart_id = c.depart_id where a.settlement_flag = 1 GROUP BY a.patient_id")
    public List<FtReportMealVo> listPayoff();

//    根据foods列表和ft_food 的价格，计算列表中菜单的总价
//    select d.*, (select sum(price) from ft_food f where FIND_IN_SET(f.food_id,d.foods)) as price from ft_food_demand d
    // 根据病患配餐表，生成次日报餐记录，并通过ft_food 菜品价格计算菜单总价
    @Insert("INSERT INTO ft_report_meals (create_at,type,patient_id,foods,settlement_flag,price,open_flag,nutrition_food_flag,nutrition_food_id,nutrition_food_price) " +
            "SELECT date_add(now(), INTERVAL 1 DAY), d.type, d.patient_id, d.foods,0," +
            "(SELECT sum(price) FROM ft_food f WHERE FIND_IN_SET(f.food_id, d.foods)) AS price," +
            "d.open_flag,0,d.nutrition_food_id, e.price as nutrition_food_price" +
            "FROM ft_food_demand d" +
            "LEFT JOIN ft_patient p ON p.patient_id = d.patient_id AND p.off_flag = 0" +
            "LEFT JOIN ft_nutrition_food e ON e.id = d.nutrition_food_id")
    public void insertTomorrowReportMeal();

    @Update("UPDATE ft_report_meals set dining_flag =  1, dining_at = now() where type  = 1 and   create_at =CURDATE()")
    Integer updateBreakfastDinnerFlag();

    @Update("UPDATE ft_report_meals set dining_flag =  1, dining_at = now() where type  = 2 and   create_at =CURDATE()")
    Integer updateLunchDinnerFlag();

    @Update("UPDATE ft_report_meals set dining_flag =  1, dining_at = now() where type = 3  and   create_at =CURDATE()")
    Integer updateDinnerDinnerFlag();

    @Update("UPDATE ft_report_meals set dining_flag =  1, dining_at = now() where type = 4 and open_flag = 1  and   create_at =CURDATE()")
    Integer updateAdditionDinnerFlag();

}
