package com.ruoyi.product.mapper;

import com.ruoyi.common.core.mybatisplus.cache.MybatisPlusRedisCache;
import com.ruoyi.common.core.mybatisplus.core.BaseMapperPlus;
import com.ruoyi.product.domain.ProItemRelation;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * 商品关系（预约记录、优惠券）Mapper接口
 *
 * @author ruoyi
 * @date 2021-04-09
 */
@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface ProItemRelationMapper extends BaseMapperPlus<ProItemRelation> {


}
