package com.ruoyi.work.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.work.domain.ActProcess;
import com.ruoyi.work.domain.vo.ActProcessVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 流程运作Mapper接口
 *
 * @author ruoyi
 * @date 2023-02-03
 */
@Mapper
public interface ActProcessMapper extends BaseMapperPlus<ActProcessMapper, ActProcess, ActProcessVo> {

    Page<ActProcess> selectVoListPage(@Param("page") Page<ActProcess> page, @Param("actProcess") ActProcess actProcess);

    Page<ActProcess> selectCCList(@Param("page") Page<ActProcess> page, @Param("actProcess") ActProcess actProcess);

    Page<ActProcess> selectSpecificList(@Param("page") Page<ActProcess> page, @Param("actProcess") ActProcess actProcess);

    ActProcess selectByIdResultBean2BusinessId(@Param("id") Long id);
}
