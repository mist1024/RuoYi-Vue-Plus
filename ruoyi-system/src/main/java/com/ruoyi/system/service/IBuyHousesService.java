package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.bo.BuyHousesBo;
import com.ruoyi.system.domain.vo.BuyHousesVo;

import java.util.Collection;
import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author ruoyi
 * @date 2023-02-24
 */
public interface IBuyHousesService {

    /**
     * 查询【请填写功能名称】
     */
    BuyHousesVo queryById(Long id);

    /**
     * 查询【请填写功能名称】列表
     */
    TableDataInfo<BuyHousesVo> queryPageList(BuyHousesBo bo, PageQuery pageQuery);

    /**
     * 查询【请填写功能名称】列表
     */
    List<BuyHousesVo> queryList(BuyHousesBo bo);

    /**
     * 新增【请填写功能名称】
     */
    Boolean insertByBo(BuyHousesBo bo);

    /**
     * 修改【请填写功能名称】
     */
    Boolean updateByBo(BuyHousesBo bo);

    /**
     * 校验并批量删除【请填写功能名称】信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    R<?> getMaterialInfo(BuyHousesBo bo);
}
