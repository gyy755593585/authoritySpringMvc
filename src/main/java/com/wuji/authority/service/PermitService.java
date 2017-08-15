/**
 *
 */
package com.wuji.authority.service;

import java.util.List;

import com.wuji.authority.model.Permit;
import com.wuji.authority.model.Role;
import com.wuji.authority.model.RolePermit;
import com.wuji.authority.vo.Tree;
import com.wuji.basic.service.BaseService;

/**
 * @author Yayun
 *
 */
public interface PermitService extends BaseService<Permit>

{

	/**
	 * @param id
	 * @return
	 */
	List<Permit> findPermitByRoleId(Long roleId);

	RolePermit addPermitForRole(Role role, Permit permit);

	/**
	 * @return
	 */
	List<Tree> findAllTree(Long id);

}
