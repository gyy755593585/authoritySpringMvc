/**
 *
 */
package com.wuji.authority.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wuji.authority.dao.RoleDao;
import com.wuji.authority.dao.UserDao;
import com.wuji.authority.dao.UserRoleDao;
import com.wuji.authority.model.Role;
import com.wuji.authority.model.User;
import com.wuji.authority.model.UserRole;
import com.wuji.authority.service.UserService;
import com.wuji.authority.shiro.PasswordHelper;
import com.wuji.basic.model.Pager;
import com.wuji.basic.model.SystemException;

/**
 * @author Yayun
 *
 */
@Transactional
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private PasswordHelper passwordHelper;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wuji.basic.service.BaseService#add(java.lang.Object)
	 */
	@Override
	public User add(User user) {
		User existUser = this.userDao.findByUserName(user.getUserName());
		if (existUser != null) {
			throw new SystemException("用户名称已存在");
		}
		this.passwordHelper.encryptPassword(user);
		return this.userDao.add(user);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wuji.basic.service.BaseService#update(java.lang.Object)
	 */
	@Override
	public void update(User user) {
		this.passwordHelper.encryptPassword(user);
		this.userDao.update(user);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wuji.basic.service.BaseService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		this.userRoleDao.deleteByUserId(id);
		this.userDao.delete(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wuji.basic.service.BaseService#load(java.lang.Long)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public User load(Long id) {
		return this.userDao.load(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wuji.basic.service.BaseService#findAll()
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<User> findAll() {
		return this.userDao.findAll();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.wuji.authority.service.UserService#findByUserName(java.lang.String)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public User findByUserName(String userName) {
		return this.userDao.findByUserName(userName);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wuji.authority.service.UserService#findByPage()
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Pager<User> findByPage() {
		Pager<User> result = this.userDao.findByPage();
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.wuji.authority.service.UserService#update(com.wuji.authority.model.
	 * User, java.util.List)
	 */
	@Override
	public void update(User user, long[] roleIds) {
		this.userRoleDao.deleteByUserId(user.getId());
		UserRole userRole = null;
		for (Long roleId : roleIds) {
			userRole = new UserRole();
			Role role = this.roleDao.load(roleId);
			userRole.setRole(role);
			userRole.setUser(user);
			this.userRoleDao.add(userRole);
		}
		if (user.getPassword() != null) {
			this.passwordHelper.encryptPassword(user);
		}
		this.userDao.update(user);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.wuji.authority.service.UserService#addUserByExcel(java.util.List)
	 */
	@Override
	public void addUserByExcel(List<List<Object>> excelObject) throws NoSuchAlgorithmException {
		String jmsg = "";
		int length = excelObject.size();
		this.logger.debug(String.valueOf(length));
		User user = null;
		for (int i = 0; i < length; i++) {
			jmsg = String.format("第 %d/%d 行数据,", i, length);
			user = new User();
			List<Object> list = excelObject.get(i);
			user.setUserName(list.get(0).toString());
			User existUser = this.userDao.findByUserName(user.getUserName());
			if (existUser != null) {
				throw new SystemException("用户名称已存在" + jmsg);
			}
			user.setNickName(list.get(1).toString());
			user.setPassword(list.get(2).toString());
			user.setType(1);
			user.setStatus(0);
			this.passwordHelper.encryptPassword(user);
			this.add(user);
		}
	}

}
