package com.ruoyi.system.fantang.mapper;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.fantang.domain.FtWeekMenuDao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * 每周菜单Mapper接口
 *
 * @author ft
 * @date 2020-11-27
 */
public interface FtWeekMenuDaoMapper extends BaseMapper<FtWeekMenuDao> {

    // 非聚合方式转换参考
    // SELECT a.weekday, a.dinner_type,  CONCAT(b.name ) name FROM ft_week_menu a LEFT JOIN ft_food b ON FIND_IN_SET(b.food_id,a.foods) WHERE a.weekday = '周一'
    @Select("SELECT a.weekday, a.dinner_type,  GROUP_CONCAT(b.name ) name FROM ft_week_menu a LEFT JOIN ft_food b ON FIND_IN_SET(b.food_id,a.foods) WHERE a.weekday = #{weekDay} GROUP BY a.dinner_type")
    AjaxResult getTodayMenu(String weekDay);
}
