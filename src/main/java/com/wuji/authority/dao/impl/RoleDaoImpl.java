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

package com.wuji.authority.dao.impl;

import org.springframework.stereotype.Repository;

import com.wuji.authority.dao.RoleDao;
import com.wuji.authority.model.Role;
import com.wuji.basic.dao.BaseDao;
import com.wuji.basic.model.Pager;

/**
 * @author Yayun
 *
 */
@Repository("roleDao")
public class RoleDaoImpl extends BaseDao<Role> implements RoleDao {

	@Override
	public Role add(Role t) {
		super.setCreateInfo(t);
		return super.add(t);
	}

	@Override
	public void update(Role t) {
		super.setEditInfo(t);
		super.update(t);
	}

	/* (non-Javadoc)
	 * @see com.wuji.authority.dao.RoleDao#findByPage()
	 */
	@Override
	public Pager<Role> findByPage() {
		return this.find("from Role", null);
	}
}
