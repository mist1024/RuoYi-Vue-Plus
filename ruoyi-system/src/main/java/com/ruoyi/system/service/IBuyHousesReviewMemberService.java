package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.bo.BuyHousesReviewMemberBo;
import com.ruoyi.system.domain.vo.BuyHousesReviewMemberVo;

import java.util.Collection;
import java.util.List;

/**
 * 购房复审家属关系Service接口
 *
 * @author ruoyi
 * @date 2023-03-15
 */
public interface IBuyHousesReviewMemberService {

    /**
     * 查询购房复审家属关系
     */
    BuyHousesReviewMemberVo queryById(Long id);

    /**
     * 查询购房复审家属关系列表
     */
    TableDataInfo<BuyHousesReviewMemberVo> queryPageList(BuyHousesReviewMemberBo bo, PageQuery pageQuery);

    /**
     * 查询购房复审家属关系列表
     */
    List<BuyHousesReviewMemberVo> queryList(BuyHousesReviewMemberBo bo);

    /**
     * 新增购房复审家属关系
     */
    Boolean insertByBo(BuyHousesReviewMemberBo bo);

    /**
     * 修改购房复审家属关系
     */
    Boolean updateByBo(BuyHousesReviewMemberBo bo);

    /**
     * 校验并批量删除购房复审家属关系信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
