/**
 *
 */
package com.wuji.authority.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wuji.authority.dao.UserDao;
import com.wuji.authority.model.User;
import com.wuji.basic.dao.BaseDao;
import com.wuji.basic.model.Pager;

/**
 * @author Yayun
 *
 */
@Repository("userDao")
public class UserDaoImpl extends BaseDao<User> implements UserDao {

	/* (non-Javadoc)
	 * @see com.wuji.authority.dao.UserDao#findByUserName(java.lang.String)
	 */
	@Override
	public User findByUserName(String userName) {
		List<User> list = (List<User>) this.getHibernateTemplate().find("from User u where u.userName =?", userName);
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public User add(User t) {
		super.setCreateInfo(t);
		return super.add(t);
	}

	@Override
	public void update(User t) {
		super.setEditInfo(t);
		super.update(t);
	}

	/* (non-Javadoc)
	 * @see com.wuji.authority.dao.UserDao#findByPage()
	 */
	@Override
	public Pager<User> findByPage() {
		return this.find("from User", null);
	}

}
