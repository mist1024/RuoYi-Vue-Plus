package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.SubscribeExportBo;
import com.ruoyi.system.domain.vo.SubscribeExportVo;
import com.ruoyi.system.domain.SubscribeExport;
import com.ruoyi.system.mapper.SubscribeExportMapper;
import com.ruoyi.system.service.ISubscribeExportService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 预约导出Service业务层处理
 *
 * @author ruoyi
 * @date 2023-04-20
 */
@RequiredArgsConstructor
@Service
public class SubscribeExportServiceImpl implements ISubscribeExportService {

    private final SubscribeExportMapper baseMapper;

    /**
     * 查询预约导出
     */
    @Override
    public SubscribeExportVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询预约导出列表
     */
    @Override
    public TableDataInfo<SubscribeExportVo> queryPageList(SubscribeExportBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SubscribeExport> lqw = buildQueryWrapper(bo);
        Page<SubscribeExportVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询预约导出列表
     */
    @Override
    public List<SubscribeExportVo> queryList(SubscribeExportBo bo) {
        LambdaQueryWrapper<SubscribeExport> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SubscribeExport> buildQueryWrapper(SubscribeExportBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SubscribeExport> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getPath()), SubscribeExport::getPath, bo.getPath());
        lqw.eq(StringUtils.isNotBlank(bo.getUserId()), SubscribeExport::getUserId, bo.getUserId());
        return lqw;
    }

    /**
     * 新增预约导出
     */
    @Override
    public Boolean insertByBo(SubscribeExportBo bo) {
        SubscribeExport add = BeanUtil.toBean(bo, SubscribeExport.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改预约导出
     */
    @Override
    public Boolean updateByBo(SubscribeExportBo bo) {
        SubscribeExport update = BeanUtil.toBean(bo, SubscribeExport.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SubscribeExport entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除预约导出
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
