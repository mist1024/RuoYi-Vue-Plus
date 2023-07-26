package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.core.service.IRsaSecurityService2;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.JsonUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.redis.CacheUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.RsaSecurityBo;
import com.ruoyi.system.domain.vo.RsaSecurityVo;
import com.ruoyi.common.core.domain.RsaSecurity;
import com.ruoyi.system.mapper.RsaSecurityMapper;
import com.ruoyi.system.service.IRsaSecurityService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 请求RSA数据加解密Service业务层处理
 *
 * @author ruoyi
 * @date 2023-05-17
 */
@RequiredArgsConstructor
@Service
public class RsaSecurityServiceImpl implements IRsaSecurityService, IRsaSecurityService2 {

    private final RsaSecurityMapper baseMapper;

    /**
     * 查询请求RSA数据加解密
     */
    @Override
    public RsaSecurityVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询请求RSA数据加解密列表
     */
    @Override
    public TableDataInfo<RsaSecurityVo> queryPageList(RsaSecurityBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<RsaSecurity> lqw = buildQueryWrapper(bo);
        Page<RsaSecurityVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询请求RSA数据加解密列表
     */
    @Override
    public List<RsaSecurityVo> queryList(RsaSecurityBo bo) {
        LambdaQueryWrapper<RsaSecurity> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<RsaSecurity> buildQueryWrapper(RsaSecurityBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<RsaSecurity> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getPath()), RsaSecurity::getPath, bo.getPath());
        lqw.eq(bo.getInDecode() != null, RsaSecurity::getInDecode, bo.getInDecode());
        lqw.eq(bo.getOutEncode() != null, RsaSecurity::getOutEncode, bo.getOutEncode());
        lqw.eq(StringUtils.isNotBlank(bo.getPublicKey()), RsaSecurity::getPublicKey, bo.getPublicKey());
        lqw.eq(StringUtils.isNotBlank(bo.getPrivateKey()), RsaSecurity::getPrivateKey, bo.getPrivateKey());
        lqw.eq(StringUtils.isNotBlank(bo.getMethod()), RsaSecurity::getMethod, bo.getMethod());
        lqw.eq(StringUtils.isNotBlank(bo.getRestricted()), RsaSecurity::getRestricted, bo.getRestricted());
        return lqw;
    }

    /**
     * 新增请求RSA数据加解密
     */
    @CachePut(cacheNames = CacheNames.RSA_SECURITY, key = "#bo.path+':'+#bo.method")
    @Override
    public RsaSecurity insertByBo(RsaSecurityBo bo) {
        RsaSecurity add = BeanUtil.toBean(bo, RsaSecurity.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            return baseMapper.selectById(add.getId());
        }
        throw new ServiceException("操作失败");
    }

    /**
     * 修改请求RSA数据加解密
     */
    @CachePut(cacheNames = CacheNames.RSA_SECURITY, key = "#bo.path+':'+#bo.method")
    @Override
    public RsaSecurity updateByBo(RsaSecurityBo bo) {
        RsaSecurity update = BeanUtil.toBean(bo, RsaSecurity.class);
        validEntityBeforeSave(update);
        boolean flag = baseMapper.updateById(update) > 0;
        if (flag) {
            return baseMapper.selectById(update.getId());
        }
        throw new ServiceException("操作失败");
    }



    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(RsaSecurity entity){
        //TODO 做一些数据校验,如唯一约束
        //判断path是否已经存在
        boolean exists = baseMapper.exists(
            new LambdaQueryWrapper<>(RsaSecurity.class)
                .eq(RsaSecurity::getPath, entity.getPath())
                .eq(RsaSecurity::getMethod, entity.getMethod())
                .ne(ObjectUtil.isNotNull(entity.getId()), RsaSecurity::getId, entity.getId()));
        if (exists){
            throw new ServiceException("当前路径path已存在");
        }
    }
    /**
     * 查询缓存中的path
     */
    public RsaSecurity getInfo(String path,String method){
        Object o = CacheUtils.get(CacheNames.RSA_SECURITY, path+":"+method);
        if (ObjectUtil.isNotNull(o)){
            return JsonUtils.parseObject(JsonUtils.toJsonString(o), RsaSecurity.class);
        }
        return baseMapper.selectOne(new LambdaQueryWrapper<>(RsaSecurity.class)
            .eq(RsaSecurity::getPath,path)
            .eq(RsaSecurity::getMethod,method));
    }

    public void loadingRsaSecurityCache(){
        List<RsaSecurity> rsaSecurities = baseMapper.selectList();
        CacheUtils.clear(CacheNames.RSA_SECURITY);
        rsaSecurities.forEach(r ->{
            CacheUtils.put(CacheNames.RSA_SECURITY, r.getPath()+":"+r.getMethod(), r);
        });
    }
    /**
     * 批量删除请求RSA数据加解密
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
