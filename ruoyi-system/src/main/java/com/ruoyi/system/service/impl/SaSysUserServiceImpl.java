package com.ruoyi.system.service.impl;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.dto.UserOnlineDTO;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.enums.UserType;
import com.ruoyi.common.utils.*;
import com.ruoyi.common.utils.ip.AddressUtils;
import com.ruoyi.system.service.ISaUserService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.SysLoginService;
import com.ruoyi.system.service.SysPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 系统用户服务
 *
 * @author dawn
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SaSysUserServiceImpl implements ISaUserService {

	private final ISysUserService sysUserService;
	private final SysPermissionService sysPermissionService;
	private final SysLoginService loginService;
	private final SaTokenConfig saTokenConfig;

	@Override
	public UserType getUserType() {
		return UserType.SYS_USER;
	}

	@Override
	public List<String> getPermissionList(Object loginId, String loginType) {
		Long userId = LoginUtils.parseUserId(loginId);
		SysUser user = sysUserService.getById(userId);
		Set<String> menuPermission = sysPermissionService.getMenuPermission(user);
		return new ArrayList<>(menuPermission);
	}

	@Override
	public List<String> getRoleList(Object loginId, String loginType) {
		Long userId = LoginUtils.parseUserId(loginId);
		SysUser user = sysUserService.getById(userId);
		Set<String> rolePermission = sysPermissionService.getRolePermission(user);
		return new ArrayList<>(rolePermission);
	}

	@Override
	public String login(LoginBody loginBody) {
		return loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
				loginBody.getUuid(), getUserType(), loginBody.getDeviceType());
	}

	@Override
	public void loginSuccess(String loginType, Object loginId, SaLoginModel loginModel) {
		UserAgent userAgent = UserAgentUtil.parse(ServletUtils.getRequest().getHeader("User-Agent"));
		String ip = ServletUtils.getClientIP();
		SysUser user = SecurityUtils.getUser();
		String tokenValue = StpUtil.getTokenValue();
		UserOnlineDTO userOnlineDTO = new UserOnlineDTO()
				.setIpaddr(ip)
				.setLoginLocation(AddressUtils.getRealAddressByIP(ip))
				.setBrowser(userAgent.getBrowser().getName())
				.setOs(userAgent.getOs().getName())
				.setLoginTime(System.currentTimeMillis())
				.setTokenId(tokenValue)
				.setUserName(user.getUserName());
		if (StringUtils.isNotNull(user.getDept())) {
			userOnlineDTO.setDeptName(user.getDept().getDeptName());
		}
		RedisUtils.setCacheObject(Constants.ONLINE_TOKEN_KEY + tokenValue, userOnlineDTO, saTokenConfig.getTimeout(), TimeUnit.SECONDS);
	}

}
