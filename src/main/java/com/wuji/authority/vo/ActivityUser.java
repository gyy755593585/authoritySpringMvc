/**
 *
 */
package com.wuji.authority.vo;

import java.util.Set;

/**
 * @author Yayun
 *
 */
public class ActivityUser {

	private static final long serialVersionUID = -1373760761780840081L;

	private Long id;

	private final String loginName;

	private String name;

	private boolean isAdmin;

	private Set<String> roles;

	private Set<String> permitCodes;

	public ActivityUser(String loginName) {
		super();
		this.loginName = loginName;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAdmin() {
		return this.isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Set<String> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public Set<String> getPermitCodes() {
		return this.permitCodes;
	}

	public void setPermitCodes(Set<String> permitCodes) {
		this.permitCodes = permitCodes;
	}

	public String getLoginName() {
		return this.loginName;
	}

	@Override
	public String toString() {
		return this.loginName;
	}
}
