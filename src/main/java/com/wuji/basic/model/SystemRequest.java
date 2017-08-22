package com.wuji.basic.model;

import javax.servlet.http.HttpServletRequest;

/**
 * 在service层获取应用层中的核心数据
 *
 * @author KongHao
 *
 */
public class SystemRequest {

	private HttpServletRequest request;

	private int pageSize;

	private int pageOffset;

	private int page;

	private String sort;

	private String order;

	private String realpath;

	private String currentUser;

	public String getRealpath() {
		if (this.request != null) {
			this.realpath = this.request.getSession().getServletContext().getRealPath("");
		}
		return this.realpath;
	}

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setRealpath(String realpath) {
		this.realpath = realpath;
	}

	public String getCurrentUser() {
		return this.currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	public int getPageSize() {
		return (this.pageSize <= 0) ? 15 : this.pageSize;
	}

	public int getPageOffset() {
		return (this.pageOffset <= 0) ? 0 : this.pageOffset;
	}

	public String getSort() {
		return this.sort;
	}

	public String getOrder() {
		return this.order;
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
}
