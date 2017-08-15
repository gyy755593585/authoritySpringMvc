/**
 *
 */
package com.wuji.authority.service;

import java.util.List;

import com.wuji.authority.model.Role;
import com.wuji.authority.model.User;
import com.wuji.authority.model.UserRole;
import com.wuji.authority.vo.Tree;
import com.wuji.basic.model.Pager;
import com.wuji.basic.service.BaseService;

/**
 * @author Yayun
 *
 */
public interface RoleService extends BaseService<Role> {

	List<Role> findRoleByUserName(String userName);

	UserRole addRoleForUser(User user, Role role);

	/**
	 * @param id
	 * @param permitIds
	 */
	void updateRolePermit(Long id, String permitIds);

	/**
	 * @param id
	 * @return
	 */
	List<Long> findPermitIdListByRoleId(Long id);

	/**
	 * @return
	 */
	List<Tree> findAllTree();

	/**
	 * @return
	 */
	Pager<Role> findByPager();

	/**
	 * @param permitId
	 * @return
	 */
	Pager<Role> findByPermitId(Long permitId);
}
