package org.dromara.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.common.sensitive.core.SensitiveService;
import org.dromara.common.tenant.helper.TenantHelper;
import org.springframework.stereotype.Service;

/**
 * 脱敏服务
 * 默认管理员不过滤
 * 需自行根据业务重写实现
 *
 * @author Lion Li
 * @version 3.6.0
 */
@Service
public class SysSensitiveServiceImpl implements SensitiveService {

    /**
     * 是否脱敏
     */
    @Override
    public boolean isSensitive(String roleKey,String perms) {
        if (!StpUtil.isLogin()){
            return true;
        }
        LoginUser loginUser = LoginHelper.getLoginUser();
        if (ObjectUtil.isNotNull(loginUser)) {
            boolean roleSensitiveFlag = loginUser.getRolePermission().stream().anyMatch(role -> role.contains(roleKey));
            if (roleSensitiveFlag) {
                return false;
            }
            boolean permsSensitiveFlag = loginUser.getMenuPermission().stream().anyMatch(menu -> menu.contains(perms));
            if (permsSensitiveFlag) {
                return false;
            }
        }
        if (TenantHelper.isEnable()) {
            return !LoginHelper.isSuperAdmin() && !LoginHelper.isTenantAdmin();
        }
        return !LoginHelper.isSuperAdmin();
    }

}
