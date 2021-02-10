package com.ruoyi.system.fantang.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.fantang.mapper.FtWeekMenuDaoMapper;
import com.ruoyi.system.fantang.domain.FtWeekMenuDao;
import com.ruoyi.system.fantang.service.IFtWeekMenuDaoService;

/**
 * 每周菜单Service业务层处理
 *
 * @author ft
 * @date 2020-11-27
 */
@Service
public class FtWeekMenuDaoServiceImpl extends ServiceImpl<FtWeekMenuDaoMapper, FtWeekMenuDao> implements IFtWeekMenuDaoService {

    @Override
    public AjaxResult getMenuOfDay(String weekDay) {
        return AjaxResult.success(this.baseMapper.getTodayMenu(weekDay));
    }
}
