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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.wuji.authority.model.Permit;
import com.wuji.authority.service.PermitService;
import com.wuji.authority.service.RoleService;
import com.wuji.authority.service.UserService;
import com.wuji.authority.vo.PermitVo;
import com.wuji.authority.vo.Tree;

/**
 *
 * @author Yayun
 *
 */
@Controller
public class PermitAction extends BaseAction {

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

	private Permit permit = new Permit();

	private Long id;

	private Long pid;

	public Long getPid() {
		return this.pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Permit getPermit() {
		return this.permit;
	}

	public void setPermit(Permit permit) {
		this.permit = permit;
	}

	public String index() {
		return "index";
	}

	public String permitAdd() {
		return "permitAdd";
	}

	public String permitEdit() throws Exception {
		this.id = this.permit.getId();
		this.permit = this.permitService.load(this.id);
		return "permitEdit";
	}

	public void add() {
		try {
			if (this.pid != null) {
				Permit parentPermit = this.permitService.load(this.pid);
				this.logger.info(parentPermit.getId().toString());
				this.permit.setParentPermit(parentPermit);
			}
			this.permitService.add(this.permit);
		} catch (Exception e) {
			this.renderError(e.getMessage());
			e.printStackTrace();
		}
		this.renderSuccess("权限添加成功");
	}

	public void edit() {
		try {
			Permit curPermit = this.permitService.load(this.permit.getId());
			curPermit.setPermitName(this.permit.getPermitName());
			curPermit.setPermitCode(this.permit.getPermitCode());
			this.permitService.update(curPermit);
			this.renderSuccess("权限修改成功");
		} catch (Exception e) {
			this.logger.error("修改权限失败", e);
			this.renderError("权限修改失败!");
		}
	}

	public void delete(HttpServletRequest request) {

		String ids = request.getParameter("ids");
		if (ids != null) {
			try {
				for (String id : ids.split(",")) {
					this.permitService.delete(Long.parseLong(id));

				}
				this.renderSuccess("权限删除成功");
			} catch (Exception e) {
				this.renderError(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public void changeStatus() {
		try {
			Permit sysPermit = this.permitService.load(this.permit.getId());

			this.permitService.update(sysPermit);
			this.renderSuccess("更改权限成功");
		} catch (Exception e) {
			this.renderError("更改权限失败");
			e.printStackTrace();
		}

	}

	public Object getPermitAll() {
		List<PermitVo> result = null;
		try {
			List<Permit> list = this.permitService.findAll();
			result = new ArrayList<>(list.size());
			PermitVo permitVo = null;
			for (Permit pemit : list) {
				permitVo = new PermitVo();
				permitVo.setId(pemit.getId());
				if (pemit.getParentPermit() != null) {
					permitVo.setPid(pemit.getParentPermit().getId());
				}
				permitVo.setPermitCode(pemit.getPermitCode());
				permitVo.setPermitName(pemit.getPermitName());
				result.add(permitVo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return this.renderError("获取失败");
		}
		return result;
	}

	public Object getPermitTree() {
		List<Tree> pager = null;
		try {
			pager = this.permitService.findAllTree(this.permit.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return this.renderError("获取失败");
		}
		return pager;
	}

}
