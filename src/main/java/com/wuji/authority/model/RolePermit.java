/**
 *
 */
package com.wuji.authority.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Yayun 角色和权限关联表
 *
 */
@Entity
@Table(name = "a_role_permit")
public class RolePermit extends BaseModel implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1953065253315333351L;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	@ManyToOne
	@JoinColumn(name = "permit_id")
	private Permit permit;

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Permit getPermit() {
		return this.permit;
	}

	public void setPermit(Permit permit) {
		this.permit = permit;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
