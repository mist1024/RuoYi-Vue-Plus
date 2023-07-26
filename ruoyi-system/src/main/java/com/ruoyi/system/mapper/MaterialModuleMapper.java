package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.system.domain.MaterialModule;
import com.ruoyi.system.domain.vo.MaterialModuleVo;
import org.apache.ibatis.annotations.Param;

/**
 * 材料模块Mapper接口
 *
 * @author ruoyi
 * @date 2023-03-09
 */
public interface MaterialModuleMapper extends BaseMapperPlus<MaterialModuleMapper, MaterialModule, MaterialModuleVo> {

    Page<MaterialModuleVo> selectVoPageList(@Param("page") Page<SysUser> page, @Param(Constants.WRAPPER) Wrapper<MaterialModule> queryWrapper);

}
