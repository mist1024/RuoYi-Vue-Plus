package com.ruoyi.demo.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.ruoyi.common.mybatis.core.mapper.BaseMapperPlus;
import com.ruoyi.demo.domain.TestDemoEncrypt;

/**
 * 测试加密功能
 *
 * @author Lion Li
 */
@InterceptorIgnore(tenantLine = "true")
public interface TestDemoEncryptMapper extends BaseMapperPlus<TestDemoEncryptMapper, TestDemoEncrypt, TestDemoEncrypt> {

}
