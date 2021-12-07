package com.ruoyi.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.mybatisplus.core.BaseMapperPlus;
import com.ruoyi.demo.domain.TestTree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 测试树表Mapper接口
 *
 * @author Lion Li
 * @date 2021-07-26
 */
public interface TestTreeMapper extends BaseMapperPlus<TestTree> {

    @Override
    @DataScope(deptName = "dept_id", userName = "user_id")
    List<TestTree> selectList(@Param(Constants.WRAPPER) Wrapper<TestTree> queryWrapper);
}
