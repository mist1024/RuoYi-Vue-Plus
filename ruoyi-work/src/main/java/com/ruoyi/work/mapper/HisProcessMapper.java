package com.ruoyi.work.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.work.domain.HisProcess;
import com.ruoyi.work.domain.vo.HisProcessVo;
import org.apache.ibatis.annotations.Param;

/**
 * 历史运作Mapper接口
 *
 * @author ruoyi
 * @date 2023-02-03
 */
public interface HisProcessMapper extends BaseMapperPlus<HisProcessMapper, HisProcess, HisProcessVo> {

    Page<HisProcess> selectVoListPage(@Param("page") Page<HisProcess> page, @Param("hisProcess") HisProcess hisProcess);

}
