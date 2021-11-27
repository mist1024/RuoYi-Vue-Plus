package com.ruoyi.system.service;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpInterface;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.enums.UserType;

/**
 * Sa用户服务接口
 *
 * @author dawn
 */
public interface ISaUserService extends StpInterface {

	/**
	 * 是否支持
	 *
	 * @param userType 用户类型
	 * @return 是否支持
	 */
	default boolean isSupport(UserType userType) {
		return getUserType().equals(userType);
	}

	/**
	 * 获取用户类型
	 *
	 * @return UserType
	 */
	UserType getUserType();

	/**
	 * 用户登录
	 *
	 * @param loginBody 登录请求体
	 * @return token
	 */
	default String login(LoginBody loginBody) {
		throw new UnsupportedOperationException("暂不支持登录, 用户类型:" + getUserType());
	}

	/**
	 * 登录成功后调用该接口
	 *
	 * @param loginType  登录类型
	 * @param loginId    登录ID
	 * @param loginModel 登录模式
	 */
	default void loginSuccess(String loginType, Object loginId, SaLoginModel loginModel) {

	}

}
