package com.ruoyi.system.fantang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.fantang.domain.FtStaffSubsidyDao;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 补贴流水查看Mapper接口
 *
 * @author ft
 * @date 2020-11-19
 */
public interface FtStaffSubsidyDaoMapper extends BaseMapper<FtStaffSubsidyDao> {

    Integer insertBatchStaffSubsidy(List<FtStaffSubsidyDao> ftStaffSubsidyDaoList);

    @Insert("")
    void reBalance();

    @Insert("INSERT into ft_staff_subsidy (staff_id, subsidy_type , income_type, price , consum_at) " +
            " select staff_id, #{subsidyType}, 3, balance -#{maxBalance}, now() from ft_staff_info where balance > #{maxBalance} ")
    void insertReBalance(@Param("subsidyType")String subsidyType, @Param("maxBalance") Integer maxBalance);
}
