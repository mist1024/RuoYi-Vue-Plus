package com.ruoyi.common.mybatis.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.properties.TenantProperties;
import com.ruoyi.common.satoken.utils.LoginHelper;
import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;

import java.util.List;

/**
 * 自定义租户处理器
 *
 * @author Lion Li
 */
@AllArgsConstructor
public class PlusTenantLineHandler implements TenantLineHandler {

    private final TenantProperties tenantProperties;

    @Override
    public Expression getTenantId() {
        String tenantId = LoginHelper.getTenantId();
        if (StringUtils.isNotBlank(tenantId)) {
            //多租户模式，租户id从当前用户获取
            return new LongValue(tenantId);
        }
        return new NullValue();
    }

    @Override
    public boolean ignoreTable(String tableName) {
        String tenantId = LoginHelper.getTenantId();
        // 判断是否有租户
        if (StringUtils.isNotBlank(tenantId)) {
            // 判断是否平台超级管理员，如果是平台超级管理员则拥有所有数据权限
            if (!LoginHelper.isAdmin()) {
                // 不需要过滤租户的表
                List<String> excludes = tenantProperties.getExcludes();
                // 非业务表
                excludes.addAll(List.of(
                        "gen_table",
                        "gen_table_column"
                ));
                return excludes.contains(tableName);
            }
        }
        return true;
    }

}
