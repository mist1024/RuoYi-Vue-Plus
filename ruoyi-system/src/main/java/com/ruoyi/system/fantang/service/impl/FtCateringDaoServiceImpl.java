package com.ruoyi.system.fantang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.fantang.domain.FtCateringDao;
import com.ruoyi.system.fantang.mapper.FtCateringDaoMapper;
import com.ruoyi.system.fantang.service.IFtCateringDaoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配餐功能Service业务层处理
 *
 * @author ft
 * @date 2020-12-07
 */
@Service
public class FtCateringDaoServiceImpl extends ServiceImpl<FtCateringDaoMapper, FtCateringDao> implements IFtCateringDaoService {

    @Override
    public List<FtCateringDao> listNewFormatter(FtCateringDao ftCateringDao) {
        return this.baseMapper.listNewFormatter(ftCateringDao);
    }
}
