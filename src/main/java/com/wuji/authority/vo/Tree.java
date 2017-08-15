package com.wuji.authority.vo;

import java.io.Serializable;

/**
 * EasyUI树形结构
 *
 * @author Yayun
 *
 */
public class Tree implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -9170935622272935940L;

	private Long id;

	private Long pid;

	private String text;

	private boolean checked;

	private String state = "open";

	private String iconCls;

	private Object attributes;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPid() {
		return this.pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isChecked() {
		return this.checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIconCls() {
		return this.iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Object getAttributes() {
		return this.attributes;
	}

	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}

}
