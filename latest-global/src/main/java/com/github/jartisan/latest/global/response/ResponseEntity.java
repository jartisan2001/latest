package com.github.jartisan.latest.global.response;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

/***
 * 全局返回包装类
 * @author jartisan
 *
 */
public class ResponseEntity {
	/**
	 * code
	 */
	private int code;
	
	/**
	 * msg
	 */
	private String msg;
	
	/**
	 * 参数
	 */
	private Map<String,String>  params = new HashMap<>();
	


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	public Map<String,String> addParams(String key,String val) {
		params.put(key, val);
		return params;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
