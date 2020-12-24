package com.ruoyi.system.fantang.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.system.fantang.domain.FtStaffDemandDao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;

/**
 * 员工报餐Mapper接口
 *
 * @author ft
 * @date 2020-12-07
 */
public interface FtStaffDemandDaoMapper extends BaseMapper<FtStaffDemandDao> {

    @Insert("INSERT into ft_staff_demand (staff_id, type, demand_mode) VALUES (#{staffId}, 1, 0),(#{staffId}, 2, 0),(#{staffId}, 3, 0)")
    Integer initDemandMode(Long staffId);
}
