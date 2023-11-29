package org.dromara.question.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.question.emum.RewardsEnum;
import org.springframework.stereotype.Service;
import org.dromara.question.domain.bo.RewardsBo;
import org.dromara.question.domain.vo.RewardsVo;
import org.dromara.question.domain.Rewards;
import org.dromara.question.mapper.RewardsMapper;
import org.dromara.question.service.IRewardsService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 奖品管理Service业务层处理
 *
 * @author lvxudong
 * @date 2023-11-27
 */
@RequiredArgsConstructor
@Service
public class RewardsServiceImpl implements IRewardsService {

    private final RewardsMapper baseMapper;

    /**
     * 查询奖品管理
     */
    @Override
    public RewardsVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询奖品管理列表
     */
    @Override
    public TableDataInfo<RewardsVo> queryPageList(RewardsBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Rewards> lqw = buildQueryWrapper(bo);
        Page<RewardsVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        TableDataInfo<RewardsVo> build = TableDataInfo.build(result);
        build.getRows().forEach(p-> p.setTypeName(RewardsEnum.getNameByValue(p.getType())));
        return TableDataInfo.build(result);
    }

    /**
     * 查询奖品管理列表
     */
    @Override
    public List<RewardsVo> queryList(RewardsBo bo) {
        LambdaQueryWrapper<Rewards> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Rewards> buildQueryWrapper(RewardsBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Rewards> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getType() != null, Rewards::getType, bo.getType());
        lqw.like(StringUtils.isNotBlank(bo.getName()), Rewards::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getImage()), Rewards::getImage, bo.getImage());
        lqw.eq(StringUtils.isNotBlank(bo.getImgDescribe()), Rewards::getImgDescribe, bo.getImgDescribe());
        return lqw;
    }

    /**
     * 新增奖品管理
     */
    @Override
    public Boolean insertByBo(RewardsBo bo) {
        Rewards add = MapstructUtils.convert(bo, Rewards.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改奖品管理
     */
    @Override
    public Boolean updateByBo(RewardsBo bo) {
        Rewards update = MapstructUtils.convert(bo, Rewards.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Rewards entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除奖品管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
