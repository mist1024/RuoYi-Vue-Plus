package com.ruoyi.system.fantang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.fantang.domain.FtStaffInfoDao;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 员工管理Mapper接口
 *
 * @author ft
 * @date 2020-11-19
 */
public interface FtStaffInfoDaoMapper extends BaseMapper<FtStaffInfoDao> {

    @Select("")
    List<FtStaffInfoDao> selectStaffInfoWithDepart();
}
