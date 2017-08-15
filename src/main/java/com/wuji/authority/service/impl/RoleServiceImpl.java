
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

package com.wuji.authority.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wuji.authority.dao.PermitDao;
import com.wuji.authority.dao.RoleDao;
import com.wuji.authority.dao.RolePermitDao;
import com.wuji.authority.dao.UserRoleDao;
import com.wuji.authority.model.Permit;
import com.wuji.authority.model.Role;
import com.wuji.authority.model.RolePermit;
import com.wuji.authority.model.User;
import com.wuji.authority.model.UserRole;
import com.wuji.authority.service.RoleService;
import com.wuji.authority.vo.Tree;
import com.wuji.basic.model.Pager;

/**
 * @author Yayun
 *
 */
@Transactional
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private UserRoleDao userRoleDao;

	@Autowired
	private RolePermitDao rolePermitDao;

	@Autowired
	private PermitDao permitDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wuji.basic.service.BaseService#add(java.lang.Object)
	 */
	@Override
	public Role add(Role t) {
		return this.roleDao.add(t);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wuji.basic.service.BaseService#update(java.lang.Object)
	 */
	@Override
	public void update(Role t) {
		this.roleDao.update(t);
	}

	/*
	 * 删除角色 同时删除角色和权限关联表,角色和用户关联表中的数据
	 */
	@Override
	public void delete(Long id) {
		this.rolePermitDao.deletByRoleId(id);
		this.userRoleDao.deleteByRoleId(id);
		this.roleDao.delete(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wuji.basic.service.BaseService#load(java.lang.Long)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Role load(Long id) {
		return this.roleDao.load(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wuji.basic.service.BaseService#findAll()
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<Role> findAll() {
		return this.roleDao.findAll();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wuji.authority.service.RoleService#findRoleByUserName(java.lang.
	 * String)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<Role> findRoleByUserName(String userName) {
		return this.userRoleDao.findRoleByUserName(userName);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.wuji.authority.service.RoleService#addRoleForUser(com.wuji.authority.
	 * model.User, com.wuji.authority.model.Role)
	 */
	@Override
	public UserRole addRoleForUser(User user, Role role) {
		UserRole userRole = new UserRole();
		userRole.setRole(role);
		userRole.setUser(user);
		return this.userRoleDao.add(userRole);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.wuji.authority.service.RoleService#findPermitIdListByRoleId(java.lang
	 * .Long)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<Long> findPermitIdListByRoleId(Long id) {
		return this.rolePermitDao.findPermitIdListByRoleId(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wuji.authority.service.RoleService#findAllTree()
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<Tree> findAllTree() {
		List<Tree> result = new ArrayList<Tree>();
		List<Role> roles = this.roleDao.findAll();
		Tree tree = null;
		for (Role role : roles) {
			tree = new Tree();
			tree.setId(role.getId());
			tree.setText(role.getRoleName());
			result.add(tree);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wuji.authority.service.RoleService#findByPager()
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Pager<Role> findByPager() {
		return this.roleDao.findByPage();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.wuji.authority.service.RoleService#updateRolePermit(java.lang.Long,
	 * java.util.List)
	 */
	@Override
	public void updateRolePermit(Long id, long[] permitIds) {
		this.rolePermitDao.deletByRoleId(id);
		Role role = this.load(id);
		RolePermit rolePermit = null;
		for (long permitId : permitIds) {
			rolePermit = new RolePermit();
			Permit permit = this.permitDao.load(permitId);
			rolePermit.setPermit(permit);
			rolePermit.setRole(role);
			this.rolePermitDao.add(rolePermit);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wuji.authority.service.RoleService#findByPermitId()
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Pager<Role> findByPermitId(Long permitId) {
		Pager<RolePermit> pager = this.rolePermitDao.findByPermitId(permitId);
		Pager<Role> result = new Pager<Role>();
		List<Role> roles = new ArrayList<>();
		List<RolePermit> rows = pager.getRows();
		for (RolePermit rolePermit : rows) {
			roles.add(rolePermit.getRole());
		}
		result.setRows(roles);
		result.setTotal(pager.getTotal());
		return result;
	}

}
