package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.bo.BuyHousesMemberBo;
import com.ruoyi.system.domain.vo.BuyHousesMemberVo;

import java.util.Collection;
import java.util.List;

/**
 * 购房家属关系Service接口
 *
 * @author ruoyi
 * @date 2023-03-15
 */
public interface IBuyHousesMemberService {

    /**
     * 查询购房家属关系
     */
    BuyHousesMemberVo queryById(Long id);

    /**
     * 查询购房家属关系列表
     */
    TableDataInfo<BuyHousesMemberVo> queryPageList(BuyHousesMemberBo bo, PageQuery pageQuery);

    /**
     * 查询购房家属关系列表
     */
    List<BuyHousesMemberVo> queryList(BuyHousesMemberBo bo);

    /**
     * 新增购房家属关系
     */
    Boolean insertByBo(BuyHousesMemberBo bo);

    /**
     * 修改购房家属关系
     */
    Boolean updateByBo(BuyHousesMemberBo bo);

    /**
     * 校验并批量删除购房家属关系信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
