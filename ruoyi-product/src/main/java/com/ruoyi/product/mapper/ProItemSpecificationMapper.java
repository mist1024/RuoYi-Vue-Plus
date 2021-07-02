package com.ruoyi.product.mapper;

import com.ruoyi.common.core.mybatisplus.cache.MybatisPlusRedisCache;
import com.ruoyi.common.core.mybatisplus.core.BaseMapperPlus;
import com.ruoyi.product.domain.ProItemSpecification;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * 商品规格Mapper接口
 *
 * @author ruoyi
 * @date 2021-06-30
 */
// 如使需切换数据源 请勿使用缓存 会造成数据不一致现象
@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface ProItemSpecificationMapper extends BaseMapperPlus<ProItemSpecification> {

}
