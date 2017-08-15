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

package com.wuji.authority.dao;

import java.util.List;

import com.wuji.authority.model.Permit;
import com.wuji.authority.model.RolePermit;
import com.wuji.basic.dao.IBaseDao;
import com.wuji.basic.model.Pager;

/**
 * @author Yayun
 *
 */
public interface RolePermitDao extends IBaseDao<RolePermit> {

	/**
	 * 通过角色id查询权限
	 *
	 * @param roleId
	 * @return
	 */
	List<Permit> findPermitByRoleId(Long roleId);

	void deletByRoleId(Long roleId);

	void deletByPermitId(Long permitId);

	/**
	 * @param id
	 * @return
	 */
	List<Long> findPermitIdListByRoleId(Long id);

	/**
	 * @param permitId
	 * @return
	 */
	Pager<RolePermit> findByPermitId(Long permitId);

}
