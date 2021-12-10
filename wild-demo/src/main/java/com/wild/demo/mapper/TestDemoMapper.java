package com.wild.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wild.common.core.mybatisplus.core.BaseMapperPlus;
import com.wild.demo.domain.TestDemo;
import com.wild.demo.domain.vo.TestDemoVo;
import org.apache.ibatis.annotations.Param;

/**
 * 测试单表Mapper接口
 *
 * @author Lion Li
 * @date 2021-07-26
 */
public interface TestDemoMapper extends BaseMapperPlus<TestDemo> {

    Page<TestDemoVo> customPageList(@Param("page") Page<TestDemo> page, @Param("ew") Wrapper<TestDemo> wrapper);

}
