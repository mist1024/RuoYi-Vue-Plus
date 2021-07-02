package com.ruoyi.product.mapper;

import com.ruoyi.common.core.mybatisplus.cache.MybatisPlusRedisCache;
import com.ruoyi.common.core.mybatisplus.core.BaseMapperPlus;
import com.ruoyi.product.domain.ProCategory;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * 商品服务类别Mapper接口
 *
 * @author ruoyi
 * @date 2021-07-01
 */
// 如使需切换数据源 请勿使用缓存 会造成数据不一致现象
@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface ProCategoryMapper extends BaseMapperPlus<ProCategory> {

}
