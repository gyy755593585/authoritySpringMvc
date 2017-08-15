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

	private Role role = new Role();

	private Long id;

	private String permitIds;

	private Long permitId;

	public String index() {
		return "index";
	}

	public String roleAdd() {
		return "roleAdd";
	}

	public String roleEdit() {
		this.id = this.role.getId();
		try {
			this.role = this.roleService.load(this.id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "roleEdit";
	}

	public String grantPage() {
		this.id = this.role.getId();
		return "roleGrant";
	}

	public void add() {
		try {
			this.roleService.add(this.role);
		} catch (Exception e) {
			this.renderError(e.getMessage());
		}
		this.renderSuccess("角色添加成功");
	}

	public void edit() {
		try {
			this.roleService.update(this.role);
			this.renderSuccess("用户修改成功");
		} catch (Exception e) {
			this.logger.error("修改交易失败", e);
			this.renderError("用户修改失败!");
		}
	}

	public void delete(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		if (ids != null) {
			try {
				for (String id : ids.split(",")) {
					this.roleService.delete(Long.parseLong(id));
				}
				this.renderSuccess("角色删除成功");
			} catch (Exception e) {
				this.renderError(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public void grant() {
		try {
			this.logger.info(this.role.getId().toString());
			this.roleService.updateRolePermit(this.role.getId(), this.permitIds);
			this.renderSuccess("授权成功");
		} catch (Exception e) {
			this.renderError("授权失败");
			e.printStackTrace();
		}
	}

	public void changeStatus() {
		try {
			Role sysRole = this.roleService.load(this.role.getId());
			if (this.role.getType() == 0) {
				sysRole.setType(1);
			} else {
				sysRole.setType(0);
			}
			this.roleService.update(sysRole);
			this.renderSuccess("更改角色成功");
		} catch (Exception e) {
			this.renderError("更改角色失败");
			e.printStackTrace();
		}

	}

	public void findPermitIdListByRoleId() {
		this.logger.info(this.role.getId().toString());
		List<Long> permitIds = this.roleService.findPermitIdListByRoleId(this.role.getId());
		this.renderSuccess(permitIds);
	}

	public Object getRoleList() {
		Pager<Role> pager = this.roleService.findByPager();
		return pager;
	}

	public Object getRoleListByPermitId() {
		Pager<Role> pager = this.roleService.findByPermitId(this.permitId);
		return pager;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPermitIds() {
		return this.permitIds;
	}

	public void setPermitIds(String permitIds) {
		this.permitIds = permitIds;
	}

	public Long getPermitId() {
		return this.permitId;
	}

	public void setPermitId(Long permitId) {
		this.permitId = permitId;
	}

}
