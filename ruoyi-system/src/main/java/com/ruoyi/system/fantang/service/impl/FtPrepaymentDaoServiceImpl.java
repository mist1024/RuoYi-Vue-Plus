package com.ruoyi.system.fantang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.fantang.domain.FtPrepaymentDao;
import com.ruoyi.system.fantang.mapper.FtPrepaymentDaoMapper;
import com.ruoyi.system.fantang.service.IFtPrepaymentDaoService;
import com.ruoyi.system.fantang.vo.FtPrepaymentVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收费管理Service业务层处理
 *
 * @author ft
 * @date 2020-11-19
 */
@Service
public class FtPrepaymentDaoServiceImpl extends ServiceImpl<FtPrepaymentDaoMapper, FtPrepaymentDao> implements IFtPrepaymentDaoService {

    @Override
    public List<FtPrepaymentVo> listNoPrepay() {
        return this.baseMapper.listNoPrepay();
    }
}
