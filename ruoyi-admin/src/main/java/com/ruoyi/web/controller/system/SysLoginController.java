package com.ruoyi.web.controller.system;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.BaseException;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.SysPermissionService;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 登录验证
 *
 * @author ruoyi
 */
@RestController
public class SysLoginController {
	@Autowired
	private SysLoginService loginService;

	@Autowired
	private ISysMenuService menuService;

	@Autowired
	private SysPermissionService permissionService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private CaptchaService captchaService;

	/**
	 * 登录方法
	 *
	 * @param loginBody 登录信息
	 * @return 结果
	 */
	@PostMapping("/login")
	public AjaxResult login(@RequestBody LoginBody loginBody) {
		CaptchaVO vo = new CaptchaVO();
		vo.setCaptchaVerification(loginBody.getCode());
		ResponseModel verification = captchaService.verification(vo);
		if (!Objects.equals("0000", verification.getRepCode())) {
			throw new BaseException("验证码校验失败！");
		}
		// 生成令牌
		String token = loginService.login(loginBody.getUsername(), loginBody.getPassword());
		Map<String, String> result = new HashMap<>();
		result.put(Constants.TOKEN, token);
		return AjaxResult.success(result);
	}

	/**
	 * 获取用户信息
	 *
	 * @return 用户信息
	 */
	@GetMapping("getInfo")
	public AjaxResult getInfo() {
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		SysUser user = loginUser.getUser();
		// 角色集合
		Set<String> roles = permissionService.getRolePermission(user);
		// 权限集合
		Set<String> permissions = permissionService.getMenuPermission(user);
		Map<String, Object> ajax = new HashMap<>();
		ajax.put("user", user);
		ajax.put("roles", roles);
		ajax.put("permissions", permissions);
		return AjaxResult.success(ajax);
	}

	/**
	 * 获取路由信息
	 *
	 * @return 路由信息
	 */
	@GetMapping("getRouters")
	public AjaxResult getRouters() {
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		// 用户信息
		SysUser user = loginUser.getUser();
		List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getUserId());
		return AjaxResult.success(menuService.buildMenus(menus));
	}
}
