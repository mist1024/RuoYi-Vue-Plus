package com.ruoyi.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.mybatisplus.core.BaseMapperPlus;
import com.ruoyi.demo.domain.TestDemo;
import com.ruoyi.demo.domain.vo.TestDemoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 测试单表Mapper接口
 *
 * @author Lion Li
 * @date 2021-07-26
 */
public interface TestDemoMapper extends BaseMapperPlus<TestDemo> {

    @DataScope(deptName = "dept_id", userName = "user_id")
    Page<TestDemoVo> customPageList(@Param("page") Page<TestDemo> page, @Param("ew") Wrapper<TestDemo> wrapper);

    @Override
    @DataScope(deptName = "dept_id", userName = "user_id")
    <P extends IPage<TestDemo>> P selectPage(P page, @Param(Constants.WRAPPER) Wrapper<TestDemo> queryWrapper);

    @Override
    @DataScope(deptName = "dept_id", userName = "user_id")
    List<TestDemo> selectList(@Param(Constants.WRAPPER) Wrapper<TestDemo> queryWrapper);
}
