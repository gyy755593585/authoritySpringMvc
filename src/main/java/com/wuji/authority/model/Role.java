/**
 *
 */
package com.wuji.authority.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Yayun 用户角色
 */
@Entity
@Table(name = "a_role")
public class Role extends BaseModel implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8110983716043633722L;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	@Column(name = "role_name")
	private String roleName;

	/**
	 * 0为普通用户 1为管理员
	 */
	private int type;

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
