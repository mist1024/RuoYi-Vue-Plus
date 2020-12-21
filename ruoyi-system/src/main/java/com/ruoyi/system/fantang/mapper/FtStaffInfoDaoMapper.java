package com.ruoyi.system.fantang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.fantang.domain.FtStaffInfoDao;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 员工管理Mapper接口
 *
 * @author ft
 * @date 2020-11-19
 */
public interface FtStaffInfoDaoMapper extends BaseMapper<FtStaffInfoDao> {

//    @Select("SELECT a.*, b.depart_name from ft_staff_info a LEFT JOIN ft_depart b on a.depart_id = b.depart_id where staff_type = 1")
    List<FtStaffInfoDao> selectStaffInfoWithDepart(FtStaffInfoDao ftStaffInfoDao);

    @Update("update ft_staff_info set token=#{token}, login_flag=1 where staff_id=#{staff_id}")
    void updateLoginStatus(FtStaffInfoDao dao);
}
