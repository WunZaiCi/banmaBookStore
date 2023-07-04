package com.banma.bs.user.domain;
/**
 * User的领域对象
 * @author WHASSUPMAN
 *
 */
public class User {
	/*
	 * 对应数据库表
	 */
	private String uid;//主键
	private String username;//用户名
	private String password;//密码
	private String email;//邮箱
	private String code;//激活码
	private Boolean state;//激活状态
	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", password=" + password + ", email=" + email + ", code="
				+ code + ", state=" + state + "]";
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Boolean getState() {
		return state;
	}
	public void setState(Boolean state) {
		this.state = state;
	}
	
}
