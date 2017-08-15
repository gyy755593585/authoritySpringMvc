/**
 *
 */
package com.wuji.authority.util;

import java.io.Serializable;

/**
 * ajax返回的json对象
 *
 * @author Yayun
 *
 */
public class Result implements Serializable {

	public static final int SUCCESS = 1;

	public static final int FAILURE = -1;

	private static final long serialVersionUID = 5576237395711742681L;

	private boolean success = false;

	private String msg = "";

	private Object obj = null;

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return this.obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}
