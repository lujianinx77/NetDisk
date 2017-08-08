package com.lu.login.entities;
// Generated 2017-8-3 10:57:48 by Hibernate Tools 5.2.3.Final

import java.util.Date;

/**
 * Userinfo generated by hbm2java
 */
public class UserInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String email;
	private Date createtime;

	public UserInfo() {
	}

	public UserInfo(String username, String password, String email, Date createtime) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.createtime = createtime;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}
