package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.bo.MaterialProofBo;
import com.ruoyi.system.domain.vo.MaterialProofVo;

import java.util.Collection;
import java.util.List;

/**
 * 材料Service接口
 *
 * @author ruoyi
 * @date 2023-03-15
 */
public interface IMaterialProofService {

    /**
     * 查询材料
     */
    MaterialProofVo queryById(Long id);

    /**
     * 查询材料列表
     */
    TableDataInfo<MaterialProofVo> queryPageList(MaterialProofBo bo, PageQuery pageQuery);

    /**
     * 查询材料列表
     */
    List<MaterialProofVo> queryList(MaterialProofBo bo);

    /**
     * 新增材料
     */
    Boolean insertByBo(MaterialProofBo bo);

    /**
     * 修改材料
     */
    Boolean updateByBo(MaterialProofBo bo);

    /**
     * 校验并批量删除材料信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
