package com.github.jartisan.latest.dao.user.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.jartisan.latest.global.entity.AbstractPersistable;

/**   
* @Title: SysRole.java 
* @Package com.github.jartisan.latest.dao.user.entity 
* @Description: 用户角色
* @author wjl
* @date 2017年12月7日 下午3:48:45 
* @version V1.0   
*/
public class Role extends AbstractPersistable<Integer> implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 角色编码
	 */
	private String code;
	
	/**
	 * 角色名称
	 */
	private String name;
	
	

	public Role() {
		
	}


	public Role(String code, String name) {
		this.code = code;
		this.name = name;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}



	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}
