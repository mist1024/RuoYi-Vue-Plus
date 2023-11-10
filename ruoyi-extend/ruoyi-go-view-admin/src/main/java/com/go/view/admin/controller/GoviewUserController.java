package com.go.view.admin.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.go.view.admin.common.base.BaseController;
import com.go.view.admin.common.domain.AjaxResult;
import com.go.view.admin.common.util.SaTokenUtil;
import com.go.view.admin.domain.GoviewUser;
import com.go.view.admin.service.IGoviewUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/goview/sys")
@RequiredArgsConstructor
public class GoviewUserController extends BaseController {
    final IGoviewUserService goViewUserService;

	@PostMapping("/login")
	@ResponseBody
	public AjaxResult login(@RequestBody GoviewUser user, HttpServletRequest request) {

		// 判断是否登陆
		if (StpUtil.isLogin()) {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userinfo", SaTokenUtil.getUser());
			map.put("token", StpUtil.getTokenInfo());
			return success().put("data", map);
		} else {
			if (StrUtil.isNotBlank(user.getUsername()) && StrUtil.isNotBlank(user.getPassword())) {
				GoviewUser sysUser = goViewUserService.getOne(new LambdaQueryWrapper<GoviewUser>().eq(GoviewUser::getUsername, user.getUsername()).eq(GoviewUser::getPassword, SecureUtil.md5(user.getUsername())).last("LIMIT 1"));
				if (sysUser != null) {
					StpUtil.login(sysUser.getId());
					SaTokenUtil.setUser(sysUser);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userinfo", sysUser);
					map.put("token", StpUtil.getTokenInfo());

					return success().put("data", map);
				} else {
					return error(500, "账户或者密码错误");
				}
			} else {
				return error(500, "账户密码不能为空");
			}
		}

	}


	@GetMapping("/logout")
	@ResponseBody
	public AjaxResult logout() {

		// 判断是否登陆
		StpUtil.logout();

		return success();

	}


	@GetMapping("/getOssInfo")
	@ResponseBody
	public AjaxResult getOssInfo() {

		return success();

	}

}
