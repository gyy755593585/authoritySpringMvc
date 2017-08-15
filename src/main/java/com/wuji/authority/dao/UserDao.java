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

import com.wuji.authority.model.User;
import com.wuji.basic.dao.IBaseDao;
import com.wuji.basic.model.Pager;

/**
 * @author Yayun
 *
 */

public interface UserDao extends IBaseDao<User> {

	/**
	 * 通过用户名查User对象
	 *
	 * @param userName
	 * @return
	 */
	User findByUserName(String userName);

	/**
	 * @return
	 */
	Pager<User> findByPage();

}
