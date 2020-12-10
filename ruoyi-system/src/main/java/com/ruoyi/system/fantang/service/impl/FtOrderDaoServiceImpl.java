package com.ruoyi.system.fantang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.fantang.domain.FtOrderDao;
import com.ruoyi.system.fantang.mapper.FtOrderDaoMapper;
import com.ruoyi.system.fantang.service.IFtOrderDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单管理Service业务层处理
 *
 * @author ft
 * @date 2020-11-19
 */
@Service
public class FtOrderDaoServiceImpl extends ServiceImpl<FtOrderDaoMapper, FtOrderDao> implements IFtOrderDaoService {

    public void GenerateStaffTomorrowOrder() {
        this.baseMapper.GenerateStaffTomorrowOrder();

    }
}
