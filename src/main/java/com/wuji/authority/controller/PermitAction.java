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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping("/permitAction")
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

	@RequestMapping("/index")
	public String index() {
		return "/permit/index";
	}

	@RequestMapping("/permitAdd")
	public String permitAdd() {
		return "/permit/permitAdd";
	}

	@RequestMapping("/permitEdit")
	public String permitEdit(long id) throws Exception {
		Permit permit = this.permitService.load(id);
		this.request.setAttribute("permit", permit);
		return "/permit/permitEdit";
	}

	@RequestMapping("/add")
	public void add(Permit permit, Long pid) {
		try {
			if (pid != null) {
				Permit parentPermit = this.permitService.load(pid);
				this.logger.info(parentPermit.getId().toString());
				permit.setParentPermit(parentPermit);
			}
			this.permitService.add(permit);
		} catch (Exception e) {
			super.writeJson(this.renderError(e.getMessage()));
			e.printStackTrace();
		}
		super.writeJson(this.renderSuccess("权限添加成功"));
	}

	@RequestMapping("/edit")
	public void edit(Permit permit) {
		try {
			Permit curPermit = this.permitService.load(permit.getId());
			curPermit.setPermitName(permit.getPermitName());
			curPermit.setPermitCode(permit.getPermitCode());
			this.permitService.update(curPermit);
			super.writeJson(this.renderSuccess("权限修改成功"));
		} catch (Exception e) {
			this.logger.error("修改权限失败", e);
			super.writeJson(this.renderError("权限修改失败!"));
		}
	}

	@RequestMapping("/delete")
	public void delete(HttpServletRequest request) {

		String ids = request.getParameter("ids");
		if (ids != null) {
			try {
				for (String id : ids.split(",")) {
					this.permitService.delete(Long.parseLong(id));

				}
				super.writeJson(this.renderSuccess("权限删除成功"));
			} catch (Exception e) {
				super.writeJson(this.renderError(e.getMessage()));
				e.printStackTrace();
			}
		}
	}

	@RequestMapping("/getPermitAll")
	@ResponseBody
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

	@RequestMapping("/getPermitTree")
	@ResponseBody
	public Object getPermitTree(Long id) {
		List<Tree> pager = null;
		try {
			pager = this.permitService.findAllTree(id);
		} catch (Exception e) {
			e.printStackTrace();
			return this.renderError("获取失败");
		}
		return pager;
	}

}
