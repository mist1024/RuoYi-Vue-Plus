package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.helper.DataBaseHelper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.MaterialModule;
import com.ruoyi.system.domain.MaterialProof;
import com.ruoyi.system.domain.MaterialTalents;
import com.ruoyi.system.domain.bo.HousesReviewBo;
import com.ruoyi.system.domain.bo.MaterialModuleBo;
import com.ruoyi.system.domain.vo.MaterialModuleVo;
import com.ruoyi.system.domain.vo.MaterialTalentsVo;
import com.ruoyi.system.mapper.MaterialModuleMapper;
import com.ruoyi.system.mapper.MaterialProofMapper;
import com.ruoyi.system.mapper.MaterialTalentsMapper;
import com.ruoyi.system.service.IMaterialModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 材料模块Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-09
 */
@RequiredArgsConstructor
@Service
public class MaterialModuleServiceImpl implements IMaterialModuleService {

    private final MaterialModuleMapper baseMapper;

    private final MaterialTalentsMapper materialTalentsMapper;

    private final MaterialProofMapper materialProofMapper;

    /**
     * 查询材料模块
     */
    @Override
    public MaterialModuleVo queryById(Long id){
        MaterialModuleVo materialModuleVo = baseMapper.selectVoById(id);
        if (ObjectUtil.isNotNull(materialModuleVo.getAuditDept())){
            String[] split = materialModuleVo.getAuditDept().split(",");
            Long[] longs = Convert.toLongArray(split);
            materialModuleVo.setAuditDeptArr(longs);
        }
        return materialModuleVo;
    }

    /**
     * 查询材料模块列表
     */
    @Override
    public TableDataInfo<MaterialModuleVo> queryPageList(MaterialModuleBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MaterialModule> lqw = buildQueryWrapper(bo);
        Page<MaterialModuleVo> result = baseMapper.selectVoPageList(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询材料模块列表
     */
    @Override
    public List<MaterialModuleVo> queryList(MaterialModuleBo bo) {
        LambdaQueryWrapper<MaterialModule> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MaterialModule> buildQueryWrapper(MaterialModuleBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MaterialModule> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getMaterialName()), MaterialModule::getMaterialName, bo.getMaterialName());
        lqw.like(StringUtils.isNotBlank(bo.getMaterialKey()), MaterialModule::getMaterialKey, bo.getMaterialKey());
        return lqw;
    }

    /**
     * 新增材料模块
     */
    @Override
    public Boolean insertByBo(MaterialModuleBo bo) {
        MaterialModule add = BeanUtil.toBean(bo, MaterialModule.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改材料模块
     */
    @Override
    public Boolean updateByBo(MaterialModuleBo bo) {
        MaterialModule update = BeanUtil.toBean(bo, MaterialModule.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MaterialModule entity){
        //将数组转为字符串
        if (ObjectUtil.isNotNull(entity.getAuditDeptArr()) && entity.getAuditDeptArr().length>0){
            String join = StringUtils.join(entity.getAuditDeptArr(), ",");
            entity.setAuditDept(join);
        }
    }

    /**
     * 批量删除材料模块
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public R selectMaterialList(MaterialModuleBo bo) {
        LambdaQueryWrapper<MaterialModule> lqw = buildQueryWrapper(bo);
       return R.ok(baseMapper.selectVoList(lqw));
    }

    public List<MaterialModuleVo> getMaterialInfo(Map<String, Object>  map){
        Set<String> keys = map.keySet();
        LambdaQueryWrapper<MaterialTalents> eq = new LambdaQueryWrapper<MaterialTalents>()
            .eq(MaterialTalents::getTalentsValue, map.get("processKey"))
            .eq(MaterialTalents::getDelFlag,"0");
        MaterialTalentsVo materialTalentsVo = materialTalentsMapper.selectVoOne(eq);
        //根据id获取关于他下面的所有子集
        LambdaQueryWrapper<MaterialTalents> wrapper = new LambdaQueryWrapper<MaterialTalents>()
            .apply(DataBaseHelper.findInSet(materialTalentsVo.getId(), "selected"));
        List<MaterialTalents> materialTalents = materialTalentsMapper.selectList(wrapper);
        ArrayList<String> list = new ArrayList<>();
        if (!"".equals(materialTalentsVo.getMaterials()) && ObjectUtil.isNotNull(materialTalentsVo.getMaterials())){
            list.add(materialTalentsVo.getMaterials());
        }
        for (MaterialTalents materialTalent : materialTalents) {
            if (keys.contains(materialTalent.getTalentsValue())){
                //获取当前目录下的数据
                Object value = map.get(materialTalent.getTalentsValue());
                if (ObjectUtil.isNotNull(value)) {
                    List<String> collect = materialTalents.stream().filter(m -> m.getTalentsValue().equals(value) && materialTalent.getId().equals(m.getParentId()) && ObjectUtil.isNotEmpty(m.getMaterials())).map(MaterialTalents::getMaterials).collect(Collectors.toList());
                    System.out.println("collect = " + collect);
                    list.addAll(collect);
                }
            }
        }
        String join = String.join(",", list);
        List<String> strings = Arrays.asList(join.split(","));
        List<Long> collect = strings.stream().distinct().map(Long ::parseLong).collect(Collectors.toList());
        List<MaterialModuleVo> materialTalentsVos = baseMapper.selectVoBatchIds(collect);
        LambdaQueryWrapper<MaterialProof> queryWrapper = new LambdaQueryWrapper<MaterialProof>()
            .eq(MaterialProof::getHouseId, map.get("id"))
            .eq(MaterialProof::getProcessKey,map.get("processKey"));
        List<MaterialProof> materialProofs = materialProofMapper.selectList(queryWrapper);
        materialTalentsVos.forEach(e -> {
            materialProofs.forEach(m ->{
                if (e.getId().toString().equals(m.getModulePathId())){
                    e.setFile(m.getFile());
                }
            });
        });
        return materialTalentsVos;
    }
}
