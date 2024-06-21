package org.dromara.liteflow.service;

import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.liteflow.domain.bo.LiteflowChainBo;
import org.dromara.liteflow.domain.vo.LiteflowChainVo;

import java.util.Collection;
import java.util.List;

/**
 * 编排规则Service接口
 *
 * @author AprilWind
 * @date 2024-06-21
 */
public interface ILiteflowChainService {

    /**
     * 查询编排规则
     *
     * @param id 主键
     * @return 编排规则
     */
    LiteflowChainVo queryById(Long id);

    /**
     * 分页查询编排规则列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 编排规则分页列表
     */
    TableDataInfo<LiteflowChainVo> queryPageList(LiteflowChainBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的编排规则列表
     *
     * @param bo 查询条件
     * @return 编排规则列表
     */
    List<LiteflowChainVo> queryList(LiteflowChainBo bo);

    /**
     * 校验编排规则ID是否唯一
     *
     * @param chain 规则信息
     * @return 结果，true表示唯一，false表示不唯一
     */
    boolean checkChainNameUnique(LiteflowChainBo chain);

    /**
     * 新增编排规则
     *
     * @param bo 编排规则
     * @return 是否新增成功
     */
    Boolean insertByBo(LiteflowChainBo bo);

    /**
     * 修改编排规则
     *
     * @param bo 编排规则
     * @return 是否修改成功
     */
    Boolean updateByBo(LiteflowChainBo bo);

    /**
     * 校验并批量删除编排规则信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
