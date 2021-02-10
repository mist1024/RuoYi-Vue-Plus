package com.ruoyi.system.fantang.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.fantang.domain.FtSettlementDao;
import com.ruoyi.system.fantang.mapper.FtSettlementDaoMapper;
import com.ruoyi.system.fantang.service.IFtSettlementDaoService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
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

        Date settleAt = ftSettlementDao.getSettleAt();
        if (settleAt != null) {
            DateTime beginOfDay = DateUtil.beginOfDay(settleAt);
            DateTime endOfDay = DateUtil.endOfDay(settleAt);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ftSettlementDao.setBeginOfDay(sdf.format(beginOfDay));
            ftSettlementDao.setEndOfDay(sdf.format(endOfDay));
        }

        return this.baseMapper.listWithPatient(ftSettlementDao);
    }
}
