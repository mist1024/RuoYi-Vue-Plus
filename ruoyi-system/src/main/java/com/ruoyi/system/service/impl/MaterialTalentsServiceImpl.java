package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.MaterialTalents;
import com.ruoyi.system.domain.bo.MaterialTalentsBo;
import com.ruoyi.system.domain.vo.MaterialTalentsVo;
import com.ruoyi.system.mapper.MaterialTalentsMapper;
import com.ruoyi.system.service.IMaterialTalentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 材料关系Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-09
 */
@RequiredArgsConstructor
@Service
public class MaterialTalentsServiceImpl implements IMaterialTalentsService {

    private final MaterialTalentsMapper baseMapper;

    /**
     * 查询材料关系
     */
    @Override
    public MaterialTalentsVo queryById(Long id){
        MaterialTalentsVo materialTalentsVo = baseMapper.selectVoById(id);
        return materialTalentsVo;
    }


    /**
     * 查询材料关系列表
     */
    @Override
    public List<MaterialTalentsVo> queryList(MaterialTalentsBo bo) {
        return baseMapper.selectVoListSpecial (bo);
    }

    private LambdaQueryWrapper<MaterialTalents> buildQueryWrapper(MaterialTalentsBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MaterialTalents> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getTalentsName()), MaterialTalents::getTalentsName, bo.getTalentsName());
        return lqw;
    }

    /**
     * 新增材料关系
     */
    @Override
    public Boolean insertByBo(MaterialTalentsBo bo) {
        if (!bo.getParentId().equals(0)){
            //获取它父类的关系级
            LambdaQueryWrapper<MaterialTalents> wrapper = new LambdaQueryWrapper<MaterialTalents>()
                .eq(MaterialTalents::getId, bo.getParentId());
            MaterialTalents materialTalents = baseMapper.selectOne(wrapper);
            if (!bo.getParentId().equals(0L)){
                String selected = materialTalents.getSelected();
                if (ObjectUtil.isNull(selected)){
                    bo.setSelected(materialTalents.getId().toString());
                }else {
                    ArrayList<String> list = new ArrayList<>();
                    List<String> strings = Arrays.asList(materialTalents.getSelected().split(","));
                    list.addAll(strings);
                    list.add(materialTalents.getId().toString());
                    String join = StringUtils.join(list, ",");
                    bo.setSelected(join);
                }
            }
        }

        MaterialTalents add = BeanUtil.toBean(bo, MaterialTalents.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改材料关系
     */
    @Override
    public Boolean updateByBo(MaterialTalentsBo bo) {
        if (!bo.getParentId().equals(0L)){
            //获取它父类的关系级
            LambdaQueryWrapper<MaterialTalents> wrapper = new LambdaQueryWrapper<MaterialTalents>()
                .eq(MaterialTalents::getId, bo.getParentId());
            MaterialTalents materialTalents = baseMapper.selectOne(wrapper);
            String selected = materialTalents.getSelected();
            if (ObjectUtil.isEmpty(selected)){
                bo.setSelected(materialTalents.getId().toString());
            }else {
                ArrayList<String> list = new ArrayList<>();
                List<String> strings = Arrays.asList(materialTalents.getSelected().split(","));
                list.addAll(strings);
                list.add(materialTalents.getId().toString());
                String join = StringUtils.join(list, ",");
                bo.setSelected(join);
            }
        }
        MaterialTalents update = BeanUtil.toBean(bo, MaterialTalents.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MaterialTalents entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除材料关系
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
