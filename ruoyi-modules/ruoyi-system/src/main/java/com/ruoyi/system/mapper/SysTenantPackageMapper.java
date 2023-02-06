package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysTenantPackage;
import com.ruoyi.system.domain.vo.SysTenantPackageVo;
import com.ruoyi.common.mybatis.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

/**
 * 租户套餐Mapper接口
 *
 * @author Michelle.Chung
 */
public interface SysTenantPackageMapper extends BaseMapperPlus<SysTenantPackageMapper, SysTenantPackage, SysTenantPackageVo> {

    /**
     * 获取租户套餐菜单id
     *
     * @param packageId 租户套餐id
     * @return 菜单id
     */
    String selectMenuIds(@Param("packageId") Long packageId);

}
