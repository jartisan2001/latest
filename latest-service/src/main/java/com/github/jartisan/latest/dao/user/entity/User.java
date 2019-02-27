package com.github.jartisan.latest.dao.user.entity;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.jartisan.latest.global.entity.AbstractPersistable;

/**
 * @Title: User.java
 * @Package com.github.jartisan.latest.dao.user.entity
 * @Description: 用户中心
 * @author wjl
 * @date 2017年11月30日 下午4:44:13
 * @version V1.0
 */

public class User extends AbstractPersistable<Integer>{
	private static final long serialVersionUID = 1L;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 真实姓名
	 */
	private String realname;

	/**
	 * 手机
	 */
	private String phone;
	
	/***
	 * 状态 0：在职 1：离职
	 */
	private Integer state;

	/**
	 * 密码
	 */
	private String password;
	/**
	 * 创建人用户名
	 */
	private String createUserName;
	/***
	 * 用户角色
	 */
	private List<Role> roles;

	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}
