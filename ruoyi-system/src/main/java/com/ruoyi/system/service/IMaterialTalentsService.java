package com.ruoyi.system.service;

import com.ruoyi.system.domain.bo.MaterialTalentsBo;
import com.ruoyi.system.domain.vo.MaterialTalentsVo;

import java.util.Collection;
import java.util.List;

/**
 * 材料关系Service接口
 *
 * @author ruoyi
 * @date 2023-03-09
 */
public interface IMaterialTalentsService {

    /**
     * 查询材料关系
     */
    MaterialTalentsVo queryById(Long id);


    /**
     * 查询材料关系列表
     */
    List<MaterialTalentsVo> queryList(MaterialTalentsBo bo);

    /**
     * 新增材料关系
     */
    Boolean insertByBo(MaterialTalentsBo bo);

    /**
     * 修改材料关系
     */
    Boolean updateByBo(MaterialTalentsBo bo);

    /**
     * 校验并批量删除材料关系信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
