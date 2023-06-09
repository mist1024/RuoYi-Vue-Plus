package com.ruoyi.work.mapper;

import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.work.domain.TProcess;
import com.ruoyi.work.domain.vo.ProcessVo;
import org.apache.ibatis.annotations.Param;

/**
 * 流程Mapper接口
 *
 * @author ruoyi
 * @date 2023-02-03
 */
public interface ProcessMapper extends BaseMapperPlus<ProcessMapper, TProcess, ProcessVo> {

    boolean updateCommonByBusinessId(@Param("bean") String bean, @Param("processStatus") String processStatus, @Param("businessId") String businessId, @Param("step") Object step);

}
