package com.ruoyi.system.fantang.service;

import com.ruoyi.system.fantang.domain.FtSettlementDao;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 结算管理Service接口
 *
 * @author ft
 * @date 2020-12-25
 */
public interface IFtSettlementDaoService extends IService<FtSettlementDao> {

    List<FtSettlementDao> listWithPatient(FtSettlementDao ftSettlementDao);
}
