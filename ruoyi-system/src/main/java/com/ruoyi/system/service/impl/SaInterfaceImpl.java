package com.ruoyi.system.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import com.ruoyi.system.service.SaUserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Primary
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SaInterfaceImpl implements StpInterface {

	/**
	 * 用户服务
	 */
	private final SaUserServices userServices;

	@Override
	public List<String> getPermissionList(Object loginId, String loginType) {
		return userServices.getPermissionList(loginId, loginType);
	}

	@Override
	public List<String> getRoleList(Object loginId, String loginType) {
		return userServices.getRoleList(loginId, loginType);
	}
}
