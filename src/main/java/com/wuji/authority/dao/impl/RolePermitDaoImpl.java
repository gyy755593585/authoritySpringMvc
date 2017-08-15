/**
 *
 */
package com.wuji.authority.dao.impl;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.wuji.authority.dao.RolePermitDao;
import com.wuji.authority.model.Permit;
import com.wuji.authority.model.RolePermit;
import com.wuji.basic.dao.BaseDao;
import com.wuji.basic.model.Pager;

/**
 * @author Yayun
 *
 */
@Repository("rolePermitDao")
public class RolePermitDaoImpl extends BaseDao<RolePermit> implements RolePermitDao {

	/* (non-Javadoc)
	 * @see com.wuji.authority.dao.RolePermitDao#findPermitByRoleId(java.lang.Long)
	 */
	@Override
	public List<Permit> findPermitByRoleId(Long roleId) {
		return (List<Permit>) this.listObj("select rp.permit from RolePermit rp where rp.role.id=?", roleId);
	}

	@Override
	public RolePermit add(RolePermit t) {
		super.setCreateInfo(t);
		return super.add(t);
	}

	@Override
	public void update(RolePermit t) {
		super.setEditInfo(t);
		super.update(t);
	}

	/* (non-Javadoc)
	 * @see com.wuji.authority.dao.RolePermitDao#deletByRoleId(java.lang.Long)
	 */
	@Override
	public void deletByRoleId(Long roleId) {
		Query query = this.currentSession().createQuery("delete from RolePermit rp where rp.role.id=?");
		query.setParameter(0, roleId);
		query.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.wuji.authority.dao.RolePermitDao#deletByPermitId(java.lang.Long)
	 */
	@Override
	public void deletByPermitId(Long permitId) {
		Query query = this.currentSession().createQuery("delete from RolePermit rp where rp.permit.id=?");
		query.setParameter(0, permitId);
		query.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.wuji.authority.dao.RolePermitDao#findPermitIdListByRoleId(java.lang.Long)
	 */
	@Override
	public List<Long> findPermitIdListByRoleId(Long id) {
		return (List<Long>) this.getHibernateTemplate().find("select rp.permit.id from RolePermit rp where rp.role.id=?", id);
	}

	/* (non-Javadoc)
	 * @see com.wuji.authority.dao.RolePermitDao#findByPermitId(java.lang.Long)
	 */
	@Override
	public Pager<RolePermit> findByPermitId(Long permitId) {
		return this.find("from RolePermit rp where rp.permit.id=? ", permitId);
	}

}
