/**
 *
 */
package com.wuji.basic.dao;

import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.wuji.authority.model.BaseModel;
import com.wuji.authority.util.DateUtil;
import com.wuji.basic.model.Pager;
import com.wuji.basic.model.SystemRequest;
import com.wuji.basic.model.SystemRequestHolder;

/**
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class BaseDao<T> extends HibernateDaoSupport implements IBaseDao<T> {

	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * 创建一个Class的对象来获取泛型的class
	 */
	private Class<?> clz;

	protected void setCreateInfo(BaseModel model) {
		Timestamp time = DateUtil.getNowFull();
		model.setCreateTime(time);
		model.setEditTime(time);
		model.setCreateUser(this.getSystemRequest().getCurrentUser());
		model.setEditUser(this.getSystemRequest().getCurrentUser());
	}

	protected void setEditInfo(BaseModel model) {
		Timestamp time = DateUtil.getNowFull();
		model.setEditTime(time);
		model.setEditUser(this.getSystemRequest().getCurrentUser());
	}

	protected SystemRequest getSystemRequest() {
		SystemRequest sr = SystemRequestHolder.getSystemRequest();
		if (sr == null) {
			sr = new SystemRequest();
		}
		return sr;
	}

	public Class<?> getClz() {
		if (this.clz == null) {
			// 获取泛型的Class对象
			this.clz = ((Class<?>) (((ParameterizedType) (this.getClass().getGenericSuperclass()))
					.getActualTypeArguments()[0]));
		}
		return this.clz;
	}

	@Override
	public T add(T t) {
		this.getHibernateTemplate().save(t);
		return t;
	}

	public Object addEntity(Object entity) {
		this.getHibernateTemplate().save(entity);
		return entity;
	}

	@Override
	public void update(T t) {
		this.getHibernateTemplate().update(t);
	}

	public void updateEntity(Object entity) {
		this.getHibernateTemplate().save(entity);
	}

	@Override
	public void delete(Long id) {
		this.getHibernateTemplate().delete(this.load(id));
	}

	public void deleteEntity(Object entity) {
		this.getHibernateTemplate().delete(entity);
	}

	public void saveOrUpdateEntity(Object entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	@Override
	public T load(Long id) {
		return (T) this.getHibernateTemplate().load(this.getClz(), id);
	}

	/* (non-Javadoc)
	 * @see com.wuji.basic.dao.IBaseDao#findAll()
	 */
	@Override
	public List<T> findAll() {
		return (List<T>) this.getHibernateTemplate().loadAll(this.getClz());
	}

	@SuppressWarnings("rawtypes")
	public Object loadEntity(int id, Class<?> clz) {
		return this.getHibernateTemplate().load(clz, id);
	}

	public List<?> listObj(String hql, Map<String, Object> alias, Object... args) {
		hql = this.initSort(hql);
		Query<?> query = this.currentSession().createQuery(hql);
		this.setAliasParameter(query, alias);
		this.setParameter(query, args);
		return query.list();
	}

	// public List<Object[]> listObjArray(String hql,Object[] args,Map<String,Object> alias) {
	// hql = initSort(hql);
	// Query query = getSession().createQuery(hql);
	// setAliasParameter(query, alias);
	// setParameter(query, args);
	// return query.list();
	// }

	public List<T> list(String hql, Map<String, Object> alias, Object... args) {
		hql = this.initSort(hql);
		Query<T> query = this.currentSession().createQuery(hql);
		this.setAliasParameter(query, alias);
		this.setParameter(query, args);
		return query.list();
	}

	/* (non-Javadoc)
	 * @see org.konghao.baisc.dao.IBaseDao#find(java.lang.String, java.lang.Object[], java.util.Map)
	 */
	public Pager<T> find(String hql, Map<String, Object> alias, Object... args) {
		hql = this.initSort(hql);
		String cq = this.getCountHql(hql, true);
		Query<?> cquery = this.currentSession().createQuery(cq);
		Query<T> query = this.currentSession().createQuery(hql);
		// 设置别名参数
		this.setAliasParameter(query, alias);
		this.setAliasParameter(cquery, alias);
		// 设置参数
		this.setParameter(query, args);
		this.setParameter(cquery, args);
		Pager<T> pages = new Pager<T>();
		this.setPagers(query, pages);
		List<T> datas = query.list();
		pages.setRows(datas);
		long total = (Long) cquery.uniqueResult();
		pages.setTotal(total);
		return pages;
	}

	public Pager<T> findNoCount(String hql, Map<String, Object> alias, Object... args) {
		hql = this.initSort(hql);
		// String cq = getCountHql(hql,true);
		// Query cquery = getSession().createQuery(cq);
		Query<?> query = this.currentSession().createQuery(hql);
		// 设置别名参数
		this.setAliasParameter(query, alias);
		// setAliasParameter(cquery, alias);
		// 设置参数
		this.setParameter(query, args);
		// setParameter(cquery, args);
		Pager<T> pages = new Pager<T>();
		this.setPagers(query, pages);
		List<T> datas = (List<T>) query.list();
		pages.setRows(datas);
		return pages;
	}

	/* (non-Javadoc)
	 * @see org.konghao.baisc.dao.IBaseDao#updateByHql(java.lang.String, java.lang.Object[])
	 */
	public void updateByHql(String hql, Object... args) {
		Query<?> query = this.currentSession().createQuery(hql);
		this.setParameter(query, args);
		query.executeUpdate();
	}

	public <N extends Object> List<N> listBySql(String sql, Map<String, Object> alias, Class<?> clz, boolean hasEntity,
			Object... args) {
		sql = this.initSort(sql);
		Query<?> sq = this.currentSession().createNativeQuery(sql, clz);
		this.setAliasParameter(sq, alias);
		this.setParameter(sq, args);
		return (List<N>) sq.list();
	}

	public <N extends Object> Pager<N> findBySql(String sql, Map<String, Object> alias, Class<?> clz, boolean hasEntity,
			Object... args) {
		sql = this.initSort(sql);
		String cq = this.getCountHql(sql, false);
		Query<?> sq = this.currentSession().createNativeQuery(sql, clz);
		Query<?> cquery = this.currentSession().createNativeQuery(cq, clz);
		this.setAliasParameter(sq, alias);
		this.setAliasParameter(cquery, alias);
		this.setParameter(sq, args);
		this.setParameter(cquery, args);
		Pager<N> pages = new Pager<N>();
		this.setPagers(sq, pages);
		List<N> datas = (List<N>) sq.list();
		pages.setRows(datas);
		long total = ((BigInteger) cquery.uniqueResult()).longValue();
		pages.setTotal(total);
		return pages;
	}

	public Object queryObject(String hql, Map<String, Object> alias, Object... args) {
		Query<?> query = this.currentSession().createQuery(hql);
		this.setAliasParameter(query, alias);
		this.setParameter(query, args);
		return query.uniqueResult();
	}

	public <N extends Object> List<N> listBySql(String sql, Class<?> clz, boolean hasEntity, Object... args) {
		return this.listBySql(sql, null, clz, hasEntity, args);
	}

	public <N extends Object> Pager<N> findBySql(String sql, Class<?> clz, boolean hasEntity, Object... args) {
		return this.findBySql(sql, null, clz, hasEntity, args);
	}

	public Pager<T> findNoCount(String hql, Object... args) {
		return this.findNoCount(hql, null, args);
	}

	public Pager<T> find(String hql, Object... args) {
		return this.find(hql, null, args);
	}

	public List<T> list(String hql, Object... args) {
		return this.list(hql, null, args);
	}

	public List<?> listObj(String hql, Object... args) {
		return this.listObj(hql, null, args);
	}

	public Object queryObject(String hql, Object... args) {
		return this.queryObject(hql, null, args);
	}

	private String initSort(String hql) {
		String order = this.getSystemRequest().getOrder();
		String sort = this.getSystemRequest().getSort();
		if (sort != null && !"".equals(sort.trim())) {
			hql += " order by " + sort;
			if (!"desc".equals(order)) {
				hql += " asc";
			} else {
				hql += " desc";
			}
		}
		return hql;
	}

	@SuppressWarnings("rawtypes")
	private void setAliasParameter(Query<?> query, Map<String, Object> alias) {
		if (alias != null) {
			Set<String> keys = alias.keySet();
			for (String key : keys) {
				Object val = alias.get(key);
				if (val instanceof Collection) {
					// 查询条件是列表
					query.setParameterList(key, (Collection<?>) val);
				} else {
					query.setParameter(key, val);
				}
			}
		}
	}

	private void setParameter(Query<?> query, Object[] args) {
		if (args != null && args.length > 0) {
			int index = 0;
			for (Object arg : args) {
				query.setParameter(index++, arg);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.konghao.baisc.dao.IBaseDao#list(java.lang.String, java.util.Map)
	 */
	public List<T> listByAlias(String hql, Map<String, Object> alias) {
		return this.list(hql, null, alias);
	}

	@SuppressWarnings("rawtypes")
	protected void setPagers(Query<?> query, Pager<?> pages) {
		Integer pageSize = this.getSystemRequest().getPageSize();
		Integer pageOffset = this.getSystemRequest().getPageOffset();
		if (pageOffset == null || pageOffset < 0) {
			pageOffset = 0;
		}
		if (pageSize == null || pageSize < 0) {
			pageSize = 15;
		}
		pages.setOffset(pageOffset);
		pages.setSize(pageSize);
		query.setFirstResult(pageOffset).setMaxResults(pageSize);
	}

	protected String getCountHql(String hql, boolean isHql) {
		String e = hql.substring(hql.indexOf("from"));
		String c = "select count(*) " + e;
		if (isHql) {
			c = c.replaceAll("fetch", "");
		}
		return c;
	}

	/* (non-Javadoc)
	 * @see org.konghao.baisc.dao.IBaseDao#find(java.lang.String, java.util.Map)
	 */
	public Pager<T> findByAlias(String hql, Map<String, Object> alias) {
		return this.find(hql, null, alias);
	}

	public <N extends Object> List<N> listByAliasSql(String sql, Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		return this.listBySql(sql, alias, clz, hasEntity);
	}

	/* (non-Javadoc)
	 * @see org.konghao.baisc.dao.IBaseDao#findBySql(java.lang.String, java.util.Map, java.lang.Class, boolean)
	 */
	public <N extends Object> Pager<N> findByAliasSql(String sql, Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		return this.findBySql(sql, alias, clz, hasEntity);
	}

	public Object queryObjectByAlias(String hql, Map<String, Object> alias) {
		return this.queryObject(hql, null, alias);
	}

	public int getMaxOrder(Integer pid, String clz) {
		String hql = "select max(o.orderNum) from " + clz + " o where o.parent.id=" + pid;
		if (pid == null || pid == 0) {
			hql = "select max(o.orderNum) from " + clz + " o where o.parent is null";
		}
		Object obj = this.queryObject(hql);
		if (obj == null) {
			return 0;
		}
		return (Integer) obj;
	}

	public int getMaxOrder(String clz) {
		String hql = "select max(o.orderNum) from " + clz + " o ";
		Object obj = this.queryObject(hql);
		if (obj == null) {
			return 0;
		}
		return (Integer) obj;
	}

	public void updateSort(Integer[] ids, String clz) {
		int index = 1;
		String hql = "update " + clz + " m set m.orderNum=? where m.id=?";
		for (Integer id : ids) {
			this.updateByHql(hql, new Object[] { index++, id });
		}
	}

	public Object loadBySn(String sn, String clz) {
		String hql = "select c from " + clz + " c where c.sn=?";
		return this.queryObject(hql, sn);
	}

}
