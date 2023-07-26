package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.bo.MaterialModuleBo;
import com.ruoyi.system.domain.vo.MaterialModuleVo;

import java.util.Collection;
import java.util.List;

/**
 * 材料模块Service接口
 *
 * @author ruoyi
 * @date 2023-03-09
 */
public interface IMaterialModuleService {

    /**
     * 查询材料模块
     */
    MaterialModuleVo queryById(Long id);

    /**
     * 查询材料模块列表
     */
    TableDataInfo<MaterialModuleVo> queryPageList(MaterialModuleBo bo, PageQuery pageQuery);

    /**
     * 查询材料模块列表
     */
    List<MaterialModuleVo> queryList(MaterialModuleBo bo);

    /**
     * 新增材料模块
     */
    Boolean insertByBo(MaterialModuleBo bo);

    /**
     * 修改材料模块
     */
    Boolean updateByBo(MaterialModuleBo bo);

    /**
     * 校验并批量删除材料模块信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    R selectMaterialList(MaterialModuleBo bo);
}
