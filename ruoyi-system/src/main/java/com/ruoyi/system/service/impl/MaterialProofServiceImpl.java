package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.MaterialProof;
import com.ruoyi.system.domain.bo.MaterialProofBo;
import com.ruoyi.system.domain.vo.MaterialProofVo;
import com.ruoyi.system.mapper.MaterialProofMapper;
import com.ruoyi.system.service.IMaterialProofService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 材料Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-15
 */
@RequiredArgsConstructor
@Service
public class MaterialProofServiceImpl implements IMaterialProofService {

    private final MaterialProofMapper baseMapper;

    /**
     * 查询材料
     */
    @Override
    public MaterialProofVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询材料列表
     */
    @Override
    public TableDataInfo<MaterialProofVo> queryPageList(MaterialProofBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MaterialProof> lqw = buildQueryWrapper(bo);
        Page<MaterialProofVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询材料列表
     */
    @Override
    public List<MaterialProofVo> queryList(MaterialProofBo bo) {
        LambdaQueryWrapper<MaterialProof> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MaterialProof> buildQueryWrapper(MaterialProofBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MaterialProof> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getHouseId()), MaterialProof::getHouseId, bo.getHouseId());
        lqw.eq(bo.getCreateTime() != null, MaterialProof::getCreateTime, bo.getCreateTime());
        lqw.eq(bo.getStatus() != null, MaterialProof::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getModulePathId()), MaterialProof::getModulePathId, bo.getModulePathId());
        lqw.eq(StringUtils.isNotBlank(bo.getFile()), MaterialProof::getFile, bo.getFile());
        lqw.eq(StringUtils.isNotBlank(bo.getMaterialKey()), MaterialProof::getMaterialKey, bo.getMaterialKey());
        lqw.eq(StringUtils.isNotBlank(bo.getDescription()), MaterialProof::getDescription, bo.getDescription());
        lqw.eq(StringUtils.isNotBlank(bo.getAuditDept()), MaterialProof::getAuditDept, bo.getAuditDept());
        lqw.eq(StringUtils.isNotBlank(bo.getCheckType()), MaterialProof::getCheckType, bo.getCheckType());
        return lqw;
    }

    /**
     * 新增材料
     */
    @Override
    public Boolean insertByBo(MaterialProofBo bo) {
        MaterialProof add = BeanUtil.toBean(bo, MaterialProof.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改材料
     */
    @Override
    public Boolean updateByBo(MaterialProofBo bo) {
        MaterialProof update = BeanUtil.toBean(bo, MaterialProof.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MaterialProof entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除材料
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
