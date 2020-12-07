package com.ruoyi.system.fantang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.fantang.domain.FtConfigDao;
import com.ruoyi.system.fantang.mapper.FtConfigDaoMapper;
import com.ruoyi.system.fantang.service.IFtConfigDaoService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 饭堂参数Service业务层处理
 *
 * @author ft
 * @date 2020-12-07
 */
@Service
public class FtConfigDaoServiceImpl extends ServiceImpl<FtConfigDaoMapper, FtConfigDao> implements IFtConfigDaoService {

    @Override
    public Map<String, String> getDinnerTimeSetting() {
        Map<String, String> map = new HashMap<>();

        QueryWrapper<FtConfigDao> breakfastStartWrapper = new QueryWrapper<>();
        breakfastStartWrapper.eq("config_key", "breakfast_start");
        String breakfastStart = this.baseMapper.selectOne(breakfastStartWrapper).getConfigValue();
        map.put("breakfastStart", breakfastStart);

        QueryWrapper<FtConfigDao> breakfastEndWrapper = new QueryWrapper<>();
        breakfastEndWrapper.eq("config_key", "breakfast_end");
        String breakfastEnd = this.baseMapper.selectOne(breakfastStartWrapper).getConfigValue();
        map.put("breakfastEnd", breakfastEnd);

        QueryWrapper<FtConfigDao> lunchStartWrapper = new QueryWrapper<>();
        lunchStartWrapper.eq("config_key", "lunch_start");
        String lunchStart = this.baseMapper.selectOne(lunchStartWrapper).getConfigValue();
        map.put("lunchStart", lunchStart);

        QueryWrapper<FtConfigDao> lunchEndWrapper = new QueryWrapper<>();
        lunchEndWrapper.eq("config_key", "lunch_end");
        String lunchEnd = this.baseMapper.selectOne(lunchEndWrapper).getConfigValue();
        map.put("lunchEnd", lunchEnd);

        QueryWrapper<FtConfigDao> dinnerStartWrapper = new QueryWrapper<>();
        dinnerStartWrapper.eq("config_key", "dinner_start");
        String dinnerStart = this.baseMapper.selectOne(dinnerStartWrapper).getConfigValue();
        map.put("dinnerStart", dinnerStart);

        QueryWrapper<FtConfigDao> dinnerEndWrapper = new QueryWrapper<>();
        dinnerEndWrapper.eq("config_key", "dinner_end");
        String dinnerEnd = this.baseMapper.selectOne(dinnerEndWrapper).getConfigValue();
        map.put("dinnerEnd", dinnerEnd);

        return map;
    }
}
