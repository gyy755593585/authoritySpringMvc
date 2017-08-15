/**
 *
 */
package com.wuji.authority.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wuji.authority.dao.PermitDao;
import com.wuji.authority.dao.RolePermitDao;
import com.wuji.authority.model.Permit;
import com.wuji.authority.model.Role;
import com.wuji.authority.model.RolePermit;
import com.wuji.authority.service.PermitService;
import com.wuji.authority.vo.Tree;

/**
 * @author Yayun
 *
 */
@Transactional
@Service("permitService")
public class PermitServiceImpl extends BaseServiceImpl implements PermitService {

	@Autowired
	private PermitDao permitDao;

	@Autowired
	private RolePermitDao rolePermitDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wuji.basic.service.BaseService#add(java.lang.Object)
	 */
	@Override
	public Permit add(Permit t) {
		return this.permitDao.add(t);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wuji.basic.service.BaseService#update(java.lang.Object)
	 */
	@Override
	public void update(Permit t) {
		this.permitDao.update(t);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wuji.basic.service.BaseService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		this.rolePermitDao.deletByPermitId(id);
		this.permitDao.delete(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wuji.basic.service.BaseService#load(java.lang.Long)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Permit load(Long id) {
		return this.permitDao.load(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wuji.basic.service.BaseService#findAll()
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<Permit> findAll() {
		return this.permitDao.findAll();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.wuji.authority.service.PermitService#findPermitByRoleId(java.lang.
	 * Long)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<Permit> findPermitByRoleId(Long roleId) {
		return this.rolePermitDao.findPermitByRoleId(roleId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wuji.authority.service.PermitService#addPermitForRole(com.wuji.
	 * authority.model.Role, com.wuji.authority.model.Permit)
	 */
	@Override
	public RolePermit addPermitForRole(Role role, Permit permit) {
		RolePermit rolePermit = new RolePermit();
		rolePermit.setPermit(permit);
		rolePermit.setRole(role);
		return this.rolePermitDao.add(rolePermit);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wuji.authority.service.PermitService#findAllTree()
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<Tree> findAllTree(Long id) {
		List<Tree> result = new ArrayList<>();
		Tree tree = null;
		List<Permit> pemits = this.permitDao.findByPid(id);
		for (Permit permit : pemits) {
			tree = new Tree();
			tree.setId(permit.getId());
			tree.setText(permit.getPermitName());
			if (permit.getParentPermit() != null) {
				tree.setPid(permit.getParentPermit().getId());
			}
			if (this.permitDao.hasChild(permit.getId())) {
				tree.setState("closed");
			} else {
				tree.setState("open");
			}
			result.add(tree);
		}
		return result;
	}

}
