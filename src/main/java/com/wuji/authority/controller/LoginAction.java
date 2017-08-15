/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.wuji.authority.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wuji.authority.model.User;
import com.wuji.authority.service.UserService;

/**
 * 登录action 匿名用户可以访问
 *
 * @author Yayun
 *
 */
@Controller
@RequestMapping("/loginAction")
public class LoginAction extends BaseAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1201335667207850091L;

	/**
	 *
	 */

	@Autowired
	private UserService userService;
	/*
	 * @Autowired private UserRealm userRealm;
	 */

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	/**
	 * 用户登录
	 *
	 * @return
	 */
	@RequestMapping("/register")
	@ResponseBody
	public Object register(User user) {
		Object reuslt = null;
		this.logger.info("POST请求登录");
		this.logger.info(user.getUserName());
		if (StringUtils.isBlank(user.getUserName())) {
			throw new RuntimeException("用户名不能为空");
		}
		if (StringUtils.isBlank(user.getPassword())) {
			throw new RuntimeException("密码不能为空");
		}
		Subject curUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
		try {
			curUser.login(token);
			reuslt = this.renderSuccess();
		} catch (UnknownAccountException e) {
			this.renderError("账号不存在！");
			throw new RuntimeException("账号不存在！", e);
		} catch (DisabledAccountException e) {
			this.renderError("账号未启用！");
			throw new RuntimeException("账号未启用！", e);
		} catch (IncorrectCredentialsException e) {
			this.renderError("账号或密码错误！");
			throw new RuntimeException("密码错误！", e);
		} catch (Throwable e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		return reuslt;
	}

	@RequestMapping("/logout")
	public void logout() {
		this.logger.info("登出");
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		// this.httpSession.invalidate();
		// this.userRealm.clearAllCache();
		this.login();
	}

	/**
	 * 页面跳转
	 *
	 * @return
	 */
	@RequestMapping("/userInfo")
	public String userInfo(Long id) {
		this.logger.info(id.toString());
		try {
			User curUser = this.userService.load(id);
			this.request.setAttribute("user", curUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "userInfo";
	}

	/**
	 * 用户更新
	 */
	@RequestMapping("/editUserInfo")
	@ResponseBody
	public Object editUserInfo(User user) {
		try {
			User curUser = this.userService.load(user.getId());
			curUser.setNickName(user.getNickName());
			curUser.setPassword(null);
			if (StringUtils.isNotBlank(user.getPassword())) {
				curUser.setPassword(user.getPassword());
			}
			this.userService.update(curUser);
			return this.renderSuccess("用户修改成功");
		} catch (Exception e) {
			this.logger.error("修改用户失败", e);
			return this.renderError("用户修改失败!");
		}
	}
}
