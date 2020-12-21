package com.ruoyi.system.fantang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.fantang.domain.FtStaffSubsidyDao;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

/**
 * 补贴流水查看Mapper接口
 *
 * @author ft
 * @date 2020-11-19
 */
public interface FtStaffSubsidyDaoMapper extends BaseMapper<FtStaffSubsidyDao> {

    Integer insertBatchStaffSubsidy(List<FtStaffSubsidyDao> ftStaffSubsidyDaoList);
}
