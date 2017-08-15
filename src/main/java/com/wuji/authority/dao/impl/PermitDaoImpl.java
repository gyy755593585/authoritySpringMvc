/**
 *
 */
package com.wuji.authority.dao.impl;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.wuji.authority.dao.PermitDao;
import com.wuji.authority.model.Permit;
import com.wuji.basic.dao.BaseDao;
import com.wuji.basic.model.Pager;

/**
 * @author Yayun
 *
 */
@Repository("permitDao")
public class PermitDaoImpl extends BaseDao<Permit> implements PermitDao {

	@Override
	public Permit add(Permit t) {
		super.setCreateInfo(t);
		return super.add(t);
	}

	@Override
	public void update(Permit t) {
		super.setEditInfo(t);
		super.update(t);
	}

	/* (non-Javadoc)
	 * @see com.wuji.authority.dao.PermitDao#findByPage()
	 */
	@Override
	public Pager<Permit> findByPage() {
		return this.find("from Permit", null);
	}

	/* (non-Javadoc)
	 * @see com.wuji.authority.dao.PermitDao#hasChild(java.lang.Long)
	 */
	@Override
	public boolean hasChild(Long id) {
		String hql = "select count(*) from Permit p where p.parentPermit.id = ?";
		Query query = this.currentSession().createQuery(hql);
		query.setParameter(0, id);
		Long count = (Long) query.uniqueResult();
		return count > 0 ? true : false;
	}

	/* (non-Javadoc)
	 * @see com.wuji.authority.dao.PermitDao#findByPid(java.lang.Long)
	 */
	@Override
	public List<Permit> findByPid(Long id) {
		List<Permit> result = null;
		if (id != null) {
			result = (List<Permit>) this.getHibernateTemplate().find("from Permit p where p.parentPermit.id=?", id);
		} else {
			result = (List<Permit>) this.getHibernateTemplate().find("from Permit p where p.parentPermit is null", null);
		}
		return result;
	}
}
