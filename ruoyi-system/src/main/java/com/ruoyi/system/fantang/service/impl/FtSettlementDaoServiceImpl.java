package com.ruoyi.system.fantang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.fantang.domain.FtSettlementDao;
import com.ruoyi.system.fantang.mapper.FtSettlementDaoMapper;
import com.ruoyi.system.fantang.service.IFtSettlementDaoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 结算管理Service业务层处理
 *
 * @author ft
 * @date 2020-12-25
 */
@Service
public class FtSettlementDaoServiceImpl extends ServiceImpl<FtSettlementDaoMapper, FtSettlementDao> implements IFtSettlementDaoService {

    @Override
    public List<FtSettlementDao> listWithPatient(FtSettlementDao ftSettlementDao) {
        return this.baseMapper.listWithPatient(ftSettlementDao);
    }
}
