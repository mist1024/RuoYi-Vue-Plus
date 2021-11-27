package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.enums.UserType;
import com.ruoyi.system.service.ISaUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * app用户服务
 *
 * @author dawn
 */
@Service
public class SaAppUserServiceImpl implements ISaUserService {

	@Override
	public UserType getUserType() {
		return UserType.APP_USER;
	}

	@Override
	public List<String> getPermissionList(Object loginId, String loginType) {
		// TODO App用户权限获取
		throw new UnsupportedOperationException("暂不支持该操作, 用户类型:" + getUserType());
	}

	@Override
	public List<String> getRoleList(Object loginId, String loginType) {
		// TODO App用户角色获取
		throw new UnsupportedOperationException("暂不支持该操作, 用户类型:" + getUserType());
	}

	@Override
	public String login(LoginBody loginBody) {
		// TODO App用户登录
		throw new UnsupportedOperationException("暂不支持该操作, 用户类型:" + getUserType());
	}


}
