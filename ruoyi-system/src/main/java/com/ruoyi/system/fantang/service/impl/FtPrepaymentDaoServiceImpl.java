package com.ruoyi.system.fantang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.fantang.domain.FtPrepaymentVo;
import com.ruoyi.system.fantang.mapper.FtPrepaymentDaoMapper;
import com.ruoyi.system.fantang.service.IFtPrepaymentDaoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收费管理Service业务层处理
 *
 * @author ft
 * @date 2020-11-19
 */
@Service
public class FtPrepaymentDaoServiceImpl extends ServiceImpl<FtPrepaymentDaoMapper, FtPrepaymentVo> implements IFtPrepaymentDaoService {

    @Override
    public List<com.ruoyi.system.fantang.vo.FtPrepaymentVo> listNoPrepay() {
        return this.baseMapper.listNoPrepay();
    }

    @Override
    public List<com.ruoyi.system.fantang.vo.FtPrepaymentVo> listPrepay() {
        return this.baseMapper.listPrepay();
    }

    @Override
    public List<com.ruoyi.system.fantang.vo.FtPrepaymentVo> listAllPrepay() {
        return this.baseMapper.listAllPrepay();
    }

    @Override
    public int getCountById(Long patiendId) {
        QueryWrapper<FtPrepaymentVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("patiendId",patiendId);
        return this.baseMapper.selectCount(queryWrapper);

    }
}
