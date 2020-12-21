package com.ruoyi.system.fantang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.fantang.domain.FtConfigDao;
import com.ruoyi.system.fantang.mapper.FtConfigDaoMapper;
import com.ruoyi.system.fantang.service.IFtConfigDaoService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
        breakfastStartWrapper.eq("config_key", "dinner_string");
        String dinnerString = this.baseMapper.selectOne(breakfastStartWrapper).getConfigValue();
        String[] split = dinnerString.split(",");
        map.put("breakfastStart", split[0]);
        map.put("breakfastEnd", split[1]);
        map.put("lunchStart", split[2]);
        map.put("lunchEnd", split[3]);
        map.put("dinnerStart", split[4]);
        map.put("dinnerEnd", split[5]);

        return map;
    }
}
