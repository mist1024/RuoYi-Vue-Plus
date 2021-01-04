package com.ruoyi.system.fantang.mapper;

import com.ruoyi.system.fantang.domain.FtSettlementDao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 结算管理Mapper接口
 *
 * @author ft
 * @date 2020-12-25
 */
public interface FtSettlementDaoMapper extends BaseMapper<FtSettlementDao> {

    List<FtSettlementDao> listWithPatient(FtSettlementDao ftSettlementDao);
}
