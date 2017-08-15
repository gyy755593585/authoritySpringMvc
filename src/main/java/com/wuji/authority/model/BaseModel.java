package com.wuji.authority.model;

import java.sql.Timestamp;

import com.wuji.authority.util.DateUtil;

public abstract class BaseModel {

	private Timestamp createTime;

	private Timestamp editTime;

	private String createUser;

	private String editUser;

	public BaseModel() {
		this.createTime = this.editTime = DateUtil.getNowFull();
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getEditTime() {
		return this.editTime;
	}

	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getEditUser() {
		return this.editUser;
	}

	public void setEditUser(String editUser) {
		this.editUser = editUser;
	}

}
