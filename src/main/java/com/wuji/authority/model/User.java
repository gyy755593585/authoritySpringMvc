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
 * @author Yayun 系统用户
 *
 */
@Entity
@Table(name = "a_user")
public class User extends BaseModel implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 823805222231282216L;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	/**
	 * 登录名称
	 */
	@Column(name = "user_name")
	private String userName;

	/**
	 * 昵称
	 */
	@Column(name = "nick_name")
	private String nickName;

	/**
	 * 加密盐
	 */
	private String salt;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 是否可用状态 0,可用 1,停用
	 */
	private int status;

	/**
	 * 1,系统管理员 2,普通用户
	 */
	private int type;

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
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
