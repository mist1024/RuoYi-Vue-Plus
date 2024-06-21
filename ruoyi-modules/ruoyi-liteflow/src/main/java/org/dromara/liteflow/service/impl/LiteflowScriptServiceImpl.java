package org.dromara.liteflow.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yomahub.liteflow.enums.ScriptTypeEnum;
import com.yomahub.liteflow.script.validator.ScriptValidator;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.liteflow.entity.LiteflowFlowBus;
import org.dromara.common.liteflow.enums.LiteflowType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.liteflow.domain.LiteflowScript;
import org.dromara.liteflow.domain.bo.LiteflowScriptBo;
import org.dromara.liteflow.domain.vo.LiteflowScriptVo;
import org.dromara.liteflow.mapper.LiteflowScriptMapper;
import org.dromara.liteflow.service.ILiteflowScriptService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.dromara.common.liteflow.constant.LiteFlowConstants.LITEFLOW_DISABLE;
import static org.dromara.common.liteflow.constant.LiteFlowConstants.LITEFLOW_NORMAL;

/**
 * 脚本Service业务层处理
 *
 * @author AprilWind
 * @date 2024-06-21
 */
@RequiredArgsConstructor
@Service
public class LiteflowScriptServiceImpl implements ILiteflowScriptService {

    private final LiteflowScriptMapper baseMapper;

    /**
     * 查询脚本
     *
     * @param id 主键
     * @return 脚本
     */
    @Override
    public LiteflowScriptVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询脚本列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 脚本分页列表
     */
    @Override
    public TableDataInfo<LiteflowScriptVo> queryPageList(LiteflowScriptBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<LiteflowScript> lqw = buildQueryWrapper(bo);
        Page<LiteflowScriptVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的脚本列表
     *
     * @param bo 查询条件
     * @return 脚本列表
     */
    @Override
    public List<LiteflowScriptVo> queryList(LiteflowScriptBo bo) {
        LambdaQueryWrapper<LiteflowScript> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<LiteflowScript> buildQueryWrapper(LiteflowScriptBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<LiteflowScript> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getApplicationName()), LiteflowScript::getApplicationName, bo.getApplicationName());
        lqw.eq(StringUtils.isNotBlank(bo.getScriptId()), LiteflowScript::getScriptId, bo.getScriptId());
        lqw.eq(StringUtils.isNotBlank(bo.getScriptName()), LiteflowScript::getScriptName, bo.getScriptName());
        lqw.eq(StringUtils.isNotBlank(bo.getScriptLanguage()), LiteflowScript::getScriptLanguage, bo.getScriptLanguage());
        lqw.eq(StringUtils.isNotBlank(bo.getScriptType()), LiteflowScript::getScriptType, bo.getScriptType());
        lqw.eq(bo.getScriptStatus() != null, LiteflowScript::getScriptStatus, bo.getScriptStatus());
        lqw.like(StringUtils.isNotBlank(bo.getRemark()), LiteflowScript::getRemark, bo.getRemark());
        return lqw;
    }

    /**
     * 校验脚本ID是否唯一
     *
     * @param script 脚本信息
     * @return 结果，true表示唯一，false表示不唯一
     */
    @Override
    public boolean checkScriptIdUnique(LiteflowScriptBo script) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<LiteflowScript>()
            .eq(LiteflowScript::getApplicationName, script.getApplicationName())
            .eq(LiteflowScript::getScriptId, script.getScriptId())
            .ne(ObjectUtil.isNotNull(script.getId()), LiteflowScript::getId, script.getId()));
        return !exist;
    }

    /**
     * 新增脚本
     *
     * @param bo 脚本
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(LiteflowScriptBo bo) {
        LiteflowScript add = MapstructUtils.convert(bo, LiteflowScript.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改脚本
     *
     * @param bo 脚本
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(LiteflowScriptBo bo) {
        LiteflowScript update = MapstructUtils.convert(bo, LiteflowScript.class);
        validEntityBeforeSave(update);
        LiteflowScriptVo vo = baseMapper.selectVoById(update.getId());

        if (LITEFLOW_NORMAL.equals(vo.getScriptStatus())) {
            LiteflowFlowBus bus = new LiteflowFlowBus();
            bus.setId(vo.getScriptId());
            if (LITEFLOW_DISABLE.equals(update.getScriptStatus())) {
                bus.setLiteflowType(LiteflowType.UNLOAD_SCRIPT);
            } else {
                bus.setLiteflowType(LiteflowType.RELOAD_SCRIPT);
                bus.setContent(update.getScriptData());
            }
            SpringUtils.context().publishEvent(bus);
        }
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(LiteflowScript entity) {
        // 检查脚本语言是否合法
        if (!ScriptTypeEnum.checkScriptType(entity.getScriptLanguage())) {
            throw new ServiceException("脚本语言不合法！");
        }

        ScriptTypeEnum enumByDisplayName = ScriptTypeEnum.getEnumByDisplayName(entity.getScriptLanguage());
        // 验证脚本数据是否符合规范
        if (!ScriptValidator.validate(entity.getScriptData(), enumByDisplayName)) {
            // 具体错误请查看日志，暂不支持返回错误详细异常信息
            throw new ServiceException("脚本内容不规范！");
        }
    }

    /**
     * 校验并批量删除脚本信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        List<LiteflowScriptVo> liteflowScriptVos = baseMapper.selectVoList(
            new LambdaQueryWrapper<LiteflowScript>().in(LiteflowScript::getId, ids));
        if (isValid) {
            if (CollUtil.isEmpty(liteflowScriptVos)) {
                return true;
            }
        }
        List<LiteflowFlowBus> liteflowFlowBus = liteflowScriptVos.stream()
            .map(x -> {
                LiteflowFlowBus flowBus = new LiteflowFlowBus();
                flowBus.setLiteflowType(LiteflowType.UNLOAD_SCRIPT);
                flowBus.setId(x.getScriptId());
                return flowBus;
            }).collect(Collectors.toList());
        // 发布规则引擎事件
        SpringUtils.context().publishEvent(liteflowFlowBus);
        return baseMapper.deleteByIds(ids) > 0;
    }
}
