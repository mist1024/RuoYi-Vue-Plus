package com.ruoyi.system.mapper;

import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.system.domain.MaterialTalents;
import com.ruoyi.system.domain.bo.MaterialTalentsBo;
import com.ruoyi.system.domain.vo.MaterialTalentsVo;

import java.util.List;

/**
 * 材料关系Mapper接口
 *
 * @author ruoyi
 * @date 2023-03-09
 */
public interface MaterialTalentsMapper extends BaseMapperPlus<MaterialTalentsMapper, MaterialTalents, MaterialTalentsVo> {

    List<MaterialTalentsVo> selectVoListSpecial(MaterialTalentsBo materialTalents);
}
