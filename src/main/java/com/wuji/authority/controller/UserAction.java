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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.wuji.authority.model.Role;
import com.wuji.authority.model.User;
import com.wuji.authority.service.RoleService;
import com.wuji.authority.service.UserService;
import com.wuji.authority.util.ExportCSVUtil;
import com.wuji.authority.util.ExportExcelUtil;
import com.wuji.authority.util.ImportCSVUtil;
import com.wuji.authority.util.ImportExcelUtil;
import com.wuji.authority.vo.Tree;
import com.wuji.basic.model.Pager;

/**
 * 用户管理Action
 *
 * @author Yayun
 *
 */
@Controller
@RequestMapping("/userAction")
public class UserAction extends BaseAction {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	private File excelFile;// 与jsp页面的file标签的name属性一样

	private String excelFileFileName;// File对象的名称+FileName,一定要这样写，不然名称获取不到

	private File csvFile;// 与jsp页面的file标签的name属性一样

	private String csvFileFileName;// File对象的名称+FileName,一定要这样写，不然名称获取不到

	public File getCsvFile() {
		return this.csvFile;
	}

	public void setCsvFile(File csvFile) {
		this.csvFile = csvFile;
	}

	public String getCsvFileFileName() {
		return this.csvFileFileName;
	}

	public void setCsvFileFileName(String csvFileFileName) {
		this.csvFileFileName = csvFileFileName;
	}

	public File getExcelFile() {
		return this.excelFile;
	}

	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

	public String getExcelFileFileName() {
		return this.excelFileFileName;
	}

	public void setExcelFileFileName(String excelFileFileName) {
		this.excelFileFileName = excelFileFileName;
	}

	/**
	 * 页面跳转
	 *
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "user/index";
	}

	/**
	 * 页面跳转
	 *
	 * @return
	 */
	@RequestMapping("/userAdd")
	public String userAdd() {
		return "user/userAdd";
	}

	/**
	 * 页面跳转
	 *
	 * @return
	 */
	@RequestMapping("/excelUpload")
	public String excelUpload() {
		return "user/excelUpload";
	}

	/**
	 * 页面跳转
	 *
	 * @return
	 */
	@RequestMapping("/csvUpload")
	public String csvUpload() {
		return "user/csvUpload";
	}

	/**
	 * 页面跳转
	 *
	 * @return
	 */
	@RequestMapping("/userEdit")
	public String userEdit(HttpServletRequest request, long id) {
		try {
			User user = this.userService.load(id);
			List<Role> lists = this.roleService.findRoleByUserName(user.getUserName());
			List<Long> roleIds = new ArrayList<>();
			for (Role sysRole : lists) {
				this.logger.info(sysRole.getId().toString());
				roleIds.add(sysRole.getId());
			}
			request.setAttribute("roleIds", roleIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "user/userEdit";
	}

	/**
	 * 用户添加
	 */
	@RequestMapping("/add")
	public void add(User user, long[] roleIds) {
		try {
			this.userService.add(user);
			for (Long roleId : roleIds) {
				Role sysRole = this.roleService.load(roleId);
				this.roleService.addRoleForUser(user, sysRole);
			}
		} catch (Exception e) {
			this.renderError(e.getMessage());
		}
		super.writeJson(this.renderSuccess("用户添加成功"));
	}

	/**
	 * 用户更新
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Object edit(User user, long[] roleIds) {
		try {
			User sysUser = this.userService.load(user.getId());
			sysUser.setNickName(user.getNickName());
			sysUser.setStatus(user.getStatus());
			sysUser.setType(user.getType());
			sysUser.setPassword(null);
			if (StringUtils.isNotBlank(user.getPassword())) {
				sysUser.setPassword(user.getPassword());
			}

			this.logger.info(String.valueOf(roleIds[0]));
			this.userService.update(sysUser, roleIds);
			return this.renderSuccess("用户修改成功");
		} catch (Exception e) {
			this.logger.error("修改用户失败", e);
			return this.renderError("用户修改失败!");
		}
	}

	/**
	 * 用户删除
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request) {

		String ids = request.getParameter("ids");
		if (ids != null) {
			try {
				for (String id : ids.split(",")) {
					this.userService.delete(Long.parseLong(id));

				}
				super.writeJson(this.renderSuccess("用户删除成功"));
			} catch (Exception e) {
				this.renderError("用户删除失败");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 改变用户状态 0为启用 1为停用
	 */
	@RequestMapping("/changeStatus")
	public void changeStatus(long id) {
		try {
			User sysUser = this.userService.load(id);
			if (sysUser.getStatus() == 0) {
				sysUser.setStatus(1);
			} else {
				sysUser.setStatus(0);
			}
			this.userService.update(sysUser);
			super.writeJson(this.renderSuccess("更改状态成功"));
		} catch (Exception e) {
			super.writeJson(this.renderError("更改状态失败"));
			e.printStackTrace();
		}

	}

	/**
	 * 用户分页列表
	 */
	@RequestMapping("/getUserList")
	@ResponseBody
	public Object getUserList() {
		Pager<User> pager = this.userService.findByPage();
		return pager;
	}

	/**
	 * 获取角色树 可多选
	 */
	@RequestMapping("/getRoleTree")
	@ResponseBody
	public Object getRoleTree() {
		try {
			List<Tree> tree = this.roleService.findAllTree();
			return tree;
		} catch (Exception e) {
			e.printStackTrace();
			return this.renderError("获取角色失败");
		}
	}

	@RequestMapping("/exportUser")
	public void exportUser(HttpServletResponse response) {
		List list = this.userService.findAll();
		JSONArray ja = new JSONArray(list);
		Map<String, String> headMap = new LinkedHashMap<String, String>();
		headMap.put("userName", "姓名");
		headMap.put("nickName", "昵称");
		headMap.put("salt", "密码盐");
		headMap.put("password", "密码");
		headMap.put("status", "状态");
		headMap.put("type", "类型");
		ExportExcelUtil.downloadExcelFile("users", headMap, ja, response);
	}

	@RequestMapping("/exportUserByCSV")
	public void exportUserByCSV(HttpServletResponse response) {
		List list = this.userService.findAll();
		JSONArray ja = new JSONArray(list);
		Map<String, String> headMap = new LinkedHashMap<String, String>();
		headMap.put("userName", "姓名");
		headMap.put("nickName", "昵称");
		headMap.put("salt", "密码盐");
		headMap.put("password", "密码");
		headMap.put("status", "状态");
		headMap.put("type", "类型");
		ExportCSVUtil.downloadCSVFile("用户列表", headMap, ja, response);
	}

	@RequestMapping("/importExcel")
	@ResponseBody
	public Object importExcel(MultipartFile excelFile) {
		InputStream fis = null;
		try {

			fis = excelFile.getInputStream();
			this.logger.info(excelFile.getOriginalFilename());
			List<List<Object>> excelObject = ImportExcelUtil.getBankListByExcel(fis, 2,
					excelFile.getOriginalFilename());
			this.userService.addUserByExcel(excelObject);
			return this.renderSuccess();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return this.renderError(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return this.renderError(e.getMessage());
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@RequestMapping("/importCSV")
	@ResponseBody
	public Object importCSV(MultipartFile csvFile) {
		InputStream is = null;
		try {

			is = csvFile.getInputStream();
			List<List<Object>> excelObject = ImportCSVUtil.importCSVUtil(is,
					new String[] { "userName", "nickName", "password" });
			this.userService.addUserByExcel(excelObject);
			return this.renderSuccess("导入成功");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return this.renderError(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return this.renderError(e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
