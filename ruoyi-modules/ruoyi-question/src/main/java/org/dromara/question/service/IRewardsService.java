package org.dromara.question.service;

import org.dromara.question.domain.Rewards;
import org.dromara.question.domain.vo.RewardsVo;
import org.dromara.question.domain.bo.RewardsBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 奖品管理Service接口
 *
 * @author lvxudong
 * @date 2023-11-27
 */
public interface IRewardsService {

    /**
     * 查询奖品管理
     */
    RewardsVo queryById(Long id);

    /**
     * 查询奖品管理列表
     */
    TableDataInfo<RewardsVo> queryPageList(RewardsBo bo, PageQuery pageQuery);

    /**
     * 查询奖品管理列表
     */
    List<RewardsVo> queryList(RewardsBo bo);

    /**
     * 新增奖品管理
     */
    Boolean insertByBo(RewardsBo bo);

    /**
     * 修改奖品管理
     */
    Boolean updateByBo(RewardsBo bo);

    /**
     * 校验并批量删除奖品管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
