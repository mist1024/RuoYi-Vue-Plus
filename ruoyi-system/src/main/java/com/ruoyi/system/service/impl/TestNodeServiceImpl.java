package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.domain.bo.TestNodeBo;
import com.ruoyi.system.domain.vo.TestNodeVo;
import com.ruoyi.system.domain.TestNode;
import com.ruoyi.system.mapper.TestNodeMapper;
import com.ruoyi.system.service.ITestNodeService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 节点维护Service业务层处理
 *
 * @author qianlan
 * @date 2021-08-08
 */
@Service
public class TestNodeServiceImpl extends ServicePlusImpl<TestNodeMapper, TestNode, TestNodeVo> implements ITestNodeService {

    @Override
    public TestNodeVo queryById(Long id){
        return getVoById(id);
    }


    @Override
    public List<TestNodeVo> queryList(TestNodeBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<TestNode> buildQueryWrapper(TestNodeBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TestNode> lqw = Wrappers.lambdaQuery();
        lqw.like(StrUtil.isNotBlank(bo.getName()), TestNode::getName, bo.getName());
        lqw.eq(StrUtil.isNotBlank(bo.getCategary()), TestNode::getCategary, bo.getCategary());
        lqw.eq(bo.getPid() != null, TestNode::getPid, bo.getPid());
        return lqw;
    }

    @Override
    public Boolean insertByBo(TestNodeBo bo) {
        TestNode add = BeanUtil.toBean(bo, TestNode.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(TestNodeBo bo) {
        TestNode update = BeanUtil.toBean(bo, TestNode.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(TestNode entity){
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return removeByIds(ids);
    }
}
