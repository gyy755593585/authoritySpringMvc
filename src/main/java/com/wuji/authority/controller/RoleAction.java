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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wuji.authority.model.Role;
import com.wuji.authority.service.PermitService;
import com.wuji.authority.service.RoleService;
import com.wuji.authority.service.UserService;
import com.wuji.basic.model.Pager;

/**
 *
 * @author Yayun
 *
 */
@Controller
@RequestMapping("/roleAction")
public class RoleAction extends BaseAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PermitService permitService;

	@RequestMapping("/index")
	public String index() {
		return "/role/index";
	}

	@RequestMapping("/roleAdd")
	public String roleAdd() {
		return "/role/roleAdd";
	}

	@RequestMapping("/roleEdit")
	public String roleEdit(long id) {
		try {
			Role role = this.roleService.load(id);
			this.request.setAttribute("role", role);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/rolerole/Edit";
	}

	@RequestMapping("/grantPage")
	public String grantPage(long id) {
		this.request.setAttribute("id", id);
		return "/role/roleGrant";
	}

	@RequestMapping("/add")
	@ResponseBody
	public Object add(Role role) {
		try {
			this.roleService.add(role);
		} catch (Exception e) {
			return this.renderError(e.getMessage());
		}
		return this.renderSuccess("角色添加成功");
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Object edit(Role role) {
		try {
			this.roleService.update(role);
			return this.renderSuccess("用户修改成功");
		} catch (Exception e) {
			this.logger.error("修改交易失败", e);
			return this.renderError("用户修改失败!");
		}
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		if (ids != null) {
			try {
				for (String id : ids.split(",")) {
					this.roleService.delete(Long.parseLong(id));
				}
				return this.renderSuccess("角色删除成功");
			} catch (Exception e) {
				e.printStackTrace();
				return this.renderError(e.getMessage());
			}
		}
		return this.renderError("角色删除失败");
	}

	@RequestMapping("/grant")
	public void grant(long id, long[] permitIds) {
		try {
			this.logger.info(String.valueOf(id));
			this.roleService.updateRolePermit(id, permitIds);
			super.writeJson(this.renderSuccess("授权成功"));
		} catch (Exception e) {
			super.writeJson(this.renderError("授权失败"));
			e.printStackTrace();
		}
	}

	@RequestMapping("/changeStatus")
	public void changeStatus(long id) {
		try {
			Role sysRole = this.roleService.load(id);
			if (sysRole.getType() == 0) {
				sysRole.setType(1);
			} else {
				sysRole.setType(0);
			}
			this.roleService.update(sysRole);
			super.writeJson(this.renderSuccess("更改角色成功"));
		} catch (Exception e) {
			super.writeJson(this.renderError("更改角色失败"));
			e.printStackTrace();
		}

	}

	@RequestMapping("/findPermitIdListByRoleId")
	public void findPermitIdListByRoleId(long id) {
		this.logger.info(String.valueOf(id));
		List<Long> permitIds = this.roleService.findPermitIdListByRoleId(id);
		super.writeJson(this.renderSuccess(permitIds));
	}

	@RequestMapping("/getRoleList")
	@ResponseBody
	public Object getRoleList() {
		Pager<Role> pager = this.roleService.findByPager();
		return pager;
	}

	@RequestMapping("/getRoleListByPermitId")
	@ResponseBody
	public Object getRoleListByPermitId(long permitId) {
		Pager<Role> pager = this.roleService.findByPermitId(permitId);
		return pager;
	}

}
