/**
 *
 */
package com.wuji.basic.service;

import java.util.List;

/**
 * @author Yayun
 *
 */
public interface BaseService<T> {

	/**
	 * 添加对象
	 *
	 * @param t
	 * @return
	 */
	public T add(T t);

	/**
	 * 更新对象
	 *
	 * @param t
	 */
	public void update(T t);

	/**
	 * 根据id删除对象
	 *
	 * @param id
	 */
	public void delete(Long id);

	/**
	 * 根据id加载对象
	 *
	 * @param id
	 * @return
	 */
	public T load(Long id);

	/**
	 * 加载所有对象
	 *
	 * @return
	 */
	public List<T> findAll();
}
