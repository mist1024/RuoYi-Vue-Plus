package com.ruoyi.system.service;

import cn.dev33.satoken.stp.SaLoginModel;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.enums.UserType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.LoginUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务: 支持多账号体系
 *
 * @author dawn
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SaUserServices {

	/**
	 * 用户服务列表
	 */
	private final List<ISaUserService> userServices;

	/**
	 * 获取用户权限列表
	 *
	 * @param loginId   用户ID
	 * @param loginType 登录类型
	 * @return 权限列表
	 */
	public List<String> getPermissionList(Object loginId, String loginType) {
		return getUserService().getPermissionList(loginId, loginType);
	}

	/**
	 * 获取用户角色列表
	 *
	 * @param loginId   用户ID
	 * @param loginType 登录类型
	 * @return 角色列表
	 */
	public List<String> getRoleList(Object loginId, String loginType) {
		return getUserService().getRoleList(loginId, loginType);
	}

	/**
	 * 用户给登录
	 *
	 * @param loginBody 登录请求体
	 * @return token
	 */
	public String login(LoginBody loginBody) {
		UserType userType = UserType.getByDeviceType(loginBody.getDeviceType());
		return getUserService(userType).login(loginBody);
	}

	/**
	 * 登录成功后调用该接口
	 *
	 * @param loginType  登录类型
	 * @param loginId    登录ID
	 * @param loginModel 登录模式
	 */
	public void loginSuccess(String loginType, Object loginId, SaLoginModel loginModel) {
		getUserService().loginSuccess(loginType, loginId, loginModel);
	}

	private ISaUserService getUserService() {
		return getUserService(LoginUtils.getUserType());
	}

	private ISaUserService getUserService(UserType userType) {
		for (ISaUserService userService : userServices) {
			if (userService.isSupport(userType)) {
				return userService;
			}
		}
		throw new ServiceException("用户类型不支持:" + userType);
	}


}
