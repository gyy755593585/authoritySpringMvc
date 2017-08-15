/**
 *
 */
package com.wuji.authority.dao.impl;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.wuji.authority.dao.UserRoleDao;
import com.wuji.authority.model.Role;
import com.wuji.authority.model.UserRole;
import com.wuji.basic.dao.BaseDao;

/**
 * @author Yayun
 *
 */
@Repository("userRoleDao")
public class UserRoleDaoImpl extends BaseDao<UserRole> implements UserRoleDao {

	/* (non-Javadoc)
	 * @see com.wuji.authority.dao.UserRoleDao#findRoleByUserName(java.lang.String)
	 */
	@Override
	public List<Role> findRoleByUserName(String userName) {
		return (List<Role>) this.listObj("select ur.role from UserRole ur where ur.user.userName=?", userName);
	}

	@Override
	public UserRole add(UserRole t) {
		super.setCreateInfo(t);
		return super.add(t);
	}

	@Override
	public void update(UserRole t) {
		super.setEditInfo(t);
		super.update(t);
	}

	/* (non-Javadoc)
	 * @see com.wuji.authority.dao.UserRoleDao#deleteByUserId(java.lang.Long)
	 */
	@Override
	public void deleteByUserId(Long userId) {
		Query query = this.currentSession().createQuery("delete from UserRole ur where ur.user.id=?");
		query.setParameter(0, userId);
		query.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.wuji.authority.dao.UserRoleDao#deleteByRoleId(java.lang.Long)
	 */
	@Override
	public void deleteByRoleId(Long roleId) {
		Query query = this.currentSession().createQuery("delete from UserRole ur where ur.role.id=?");
		query.setParameter(0, roleId);
		query.executeUpdate();
	}
}
