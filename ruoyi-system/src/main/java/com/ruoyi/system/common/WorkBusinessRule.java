package com.ruoyi.system.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.BuyHouseCheck;
import com.ruoyi.system.domain.vo.BuyHouseCheckVo;
import com.ruoyi.system.mapper.BuyHouseCheckMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkBusinessRule {

    private static final BuyHouseCheckMapper buyHouseCheckMapper= SpringUtils.getBean(BuyHouseCheckMapper.class);

    public String getDistrict(String district){
        //先获取业务数据

        QueryWrapper<BuyHouseCheck> qw = new QueryWrapper<>();
        qw.eq("crux_key","buy_house_audit");
        qw.eq("other_key",district);
        BuyHouseCheckVo buyHouseCheckVo = buyHouseCheckMapper.selectVoOne(qw);
        return buyHouseCheckVo.getPerson();
    }
}

