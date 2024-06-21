package org.dromara.liteflow.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yomahub.liteflow.builder.el.LiteFlowChainELBuilder;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.liteflow.entity.LiteflowFlowBus;
import org.dromara.common.liteflow.enums.LiteflowType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.liteflow.domain.LiteflowChain;
import org.dromara.liteflow.domain.bo.LiteflowChainBo;
import org.dromara.liteflow.domain.vo.LiteflowChainVo;
import org.dromara.liteflow.mapper.LiteflowChainMapper;
import org.dromara.liteflow.service.ILiteflowChainService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.dromara.common.liteflow.constant.LiteFlowConstants.LITEFLOW_DISABLE;
import static org.dromara.common.liteflow.constant.LiteFlowConstants.LITEFLOW_NORMAL;

/**
 * 编排规则Service业务层处理
 *
 * @author AprilWind
 * @date 2024-06-21
 */
@RequiredArgsConstructor
@Service
public class LiteflowChainServiceImpl implements ILiteflowChainService {

    private final LiteflowChainMapper baseMapper;

    /**
     * 查询编排规则
     *
     * @param id 主键
     * @return 编排规则
     */
    @Override
    public LiteflowChainVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询编排规则列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 编排规则分页列表
     */
    @Override
    public TableDataInfo<LiteflowChainVo> queryPageList(LiteflowChainBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<LiteflowChain> lqw = buildQueryWrapper(bo);
        Page<LiteflowChainVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的编排规则列表
     *
     * @param bo 查询条件
     * @return 编排规则列表
     */
    @Override
    public List<LiteflowChainVo> queryList(LiteflowChainBo bo) {
        LambdaQueryWrapper<LiteflowChain> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<LiteflowChain> buildQueryWrapper(LiteflowChainBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<LiteflowChain> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getApplicationName()), LiteflowChain::getApplicationName, bo.getApplicationName());
        lqw.eq(StringUtils.isNotBlank(bo.getChainName()), LiteflowChain::getChainName, bo.getChainName());
        lqw.eq(bo.getChainStatus() != null, LiteflowChain::getChainStatus, bo.getChainStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getNameSpace()), LiteflowChain::getNameSpace, bo.getNameSpace());
        lqw.like(StringUtils.isNotBlank(bo.getRemark()), LiteflowChain::getRemark, bo.getRemark());
        return lqw;
    }

    /**
     * 校验编排规则ID是否唯一
     *
     * @param chain 规则信息
     * @return 结果，true表示唯一，false表示不唯一
     */
    @Override
    public boolean checkChainNameUnique(LiteflowChainBo chain) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<LiteflowChain>()
            .eq(LiteflowChain::getApplicationName, chain.getApplicationName())
            .eq(LiteflowChain::getChainName, chain.getChainName())
            .ne(ObjectUtil.isNotNull(chain.getId()), LiteflowChain::getId, chain.getId()));
        return !exist;
    }

    /**
     * 新增编排规则
     *
     * @param bo 编排规则
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(LiteflowChainBo bo) {
        LiteflowChain add = MapstructUtils.convert(bo, LiteflowChain.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改编排规则
     *
     * @param bo 编排规则
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(LiteflowChainBo bo) {
        LiteflowChain update = MapstructUtils.convert(bo, LiteflowChain.class);
        validEntityBeforeSave(update);
        LiteflowChainVo vo = baseMapper.selectVoById(bo.getId());
        if (LITEFLOW_NORMAL.equals(vo.getChainStatus())) {
            LiteflowFlowBus bus = new LiteflowFlowBus();
            bus.setId(vo.getChainName());
            if (LITEFLOW_DISABLE.equals(update.getChainStatus())) {
                bus.setLiteflowType(LiteflowType.REMOVE_CHAIN);
            } else {
                bus.setLiteflowType(LiteflowType.RELOAD_CHAIN);
                bus.setContent(update.getElStr());
            }
            SpringUtils.context().publishEvent(bus);
        }
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(LiteflowChain entity) {
        // 校验EL表达式
        if (!LiteFlowChainELBuilder.validate(entity.getElStr())) {
            throw new ServiceException("EL表达式校验失败！");
        }
        // 如果路由不为空，校验路由
        if (StringUtils.isNotBlank(entity.getRoute()) && !LiteFlowChainELBuilder.validate(entity.getRoute())) {
            throw new ServiceException("决策路由EL校验失败！");
        }
    }

    /**
     * 校验并批量删除编排规则信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        List<LiteflowChainVo> liteflowChainVos = baseMapper.selectVoList(
            new LambdaQueryWrapper<LiteflowChain>().in(LiteflowChain::getId, ids));
        if (isValid) {
            if (CollUtil.isEmpty(liteflowChainVos)) {
                return true;
            }
        }
        List<LiteflowFlowBus> liteflowFlowBus = liteflowChainVos.stream()
            .map(x -> {
                LiteflowFlowBus flowBus = new LiteflowFlowBus();
                flowBus.setLiteflowType(LiteflowType.REMOVE_CHAIN);
                flowBus.setId(x.getChainName());
                return flowBus;
            }).collect(Collectors.toList());
        // 发布规则引擎事件
        SpringUtils.context().publishEvent(liteflowFlowBus);
        return baseMapper.deleteByIds(ids) > 0;
    }
}
