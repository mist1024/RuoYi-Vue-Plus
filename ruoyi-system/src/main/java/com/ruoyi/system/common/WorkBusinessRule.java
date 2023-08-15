package com.ruoyi.system.common;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.BuyHouseCheck;
import com.ruoyi.system.domain.HousesReview;
import com.ruoyi.system.domain.MaterialModule;
import com.ruoyi.system.domain.vo.BuyHouseCheckVo;
import com.ruoyi.system.domain.vo.MaterialModuleVo;
import com.ruoyi.system.mapper.BuyHouseCheckMapper;
import com.ruoyi.system.mapper.HousesReviewMapper;
import com.ruoyi.system.service.impl.MaterialModuleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WorkBusinessRule {

    private static final BuyHouseCheckMapper buyHouseCheckMapper= SpringUtils.getBean(BuyHouseCheckMapper.class);

    private static final HousesReviewMapper housesReviewMapper = SpringUtils.getBean(HousesReviewMapper.class);
    private static final MaterialModuleServiceImpl materialModuleService = SpringUtils.getBean(MaterialModuleServiceImpl.class);

    /**
     * 购房认定受理获取审核部门
     * @param district
     * @param step
     * @return
     */
    public String getDistrict(String district,String step){
        //先获取业务数据
        QueryWrapper<BuyHouseCheck> qw = new QueryWrapper<>();
        qw.eq("crux_key","buy_house_audit");
        qw.eq("other_key",district);
        qw.eq("step",step);
        List<BuyHouseCheck> buyHouseCheckList = buyHouseCheckMapper.selectList(qw);
        if (buyHouseCheckList.size()<0){
            throw new ServiceException("获取审核人失败");
        }
        List<String> collect = buyHouseCheckList.stream().map(BuyHouseCheck::getPerson).collect(Collectors.toList());
        String join = StringUtils.join(collect, ",");
        return join;
    }

    /**
     * 购房二期获取审核部门
     * @param id
     * @return
     */
    public List<String> getCheckDept(Long id){
        HousesReview housesReview = housesReviewMapper.selectById(id);
        Map<String, Object> map = BeanUtil.beanToMap(housesReview);
        List<MaterialModuleVo> materialInfo = materialModuleService.getMaterialInfo(map);
        List<String> collect = materialInfo.stream().map(MaterialModuleVo::getAuditDept)
            .filter(c -> ObjectUtil.isNotNull(c) && ObjectUtil.isNotEmpty(c)).collect(Collectors.toList());
        return collect;
    }
}

