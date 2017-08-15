/**
 *
 */
package com.wuji.authority.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.wuji.authority.model.User;
import com.wuji.basic.model.Pager;
import com.wuji.basic.service.BaseService;

/**
 * @author Yayun
 *
 */
public interface UserService extends BaseService<User> {

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

	void update(User user, List<Long> roleIds);

	/**
	 * @param excelObject
	 * @throws NoSuchAlgorithmException
	 */
	void addUserByExcel(List<List<Object>> excelObject) throws NoSuchAlgorithmException;

}
