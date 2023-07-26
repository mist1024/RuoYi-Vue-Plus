package com.ruoyi.system.mapper;

import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.system.domain.BuyHouses;
import com.ruoyi.system.domain.vo.BuyHousesVo;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author ruoyi
 * @date 2023-02-24
 */
public interface BuyHousesMapper extends BaseMapperPlus<BuyHousesMapper, BuyHouses, BuyHousesVo> {

    List<Map> getIndexType();

    List<Map> getActProcessList();

    List<Map> getCompanyDistrict();

    List<Map> getNationality();

    List<Map> getMaritalStatus();
}
