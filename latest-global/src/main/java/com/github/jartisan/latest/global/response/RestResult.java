package com.github.jartisan.latest.global.response;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.jartisan.latest.global.enums.GlobalCode;

/***
 * @description 反回结果类
 * @author wjl
 * @ClassName: RestResult
 * @Version 版本
 * @ModifiedBy jartisan
 * @Copyright com.github.jartisan
 * @date 2017年07月25日 下午7:28:23
 */
public class RestResult<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 全局响应码
	 */
	private int status;
	/**
	 * 全局响应码说明
	 */
	private String message;
	/**
	 * 响应数据
	 */
	@JsonInclude(Include.NON_NULL) 
	private T data;


	public RestResult() {
		super();
	}


	public RestResult(T result) {
		this(GlobalCode.SUCCESS.getStatus(),GlobalCode.SUCCESS.getMessage(),null);
	}
	
	
	public RestResult(int status, String message) {
		
		this(status,message,null);
	}


	public RestResult(int status, String message, T data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public int getStatus() {
		return status;
	}


	public String getMessage() {
		return message;
	}


	public T getData() {
		return data;
	}
	
	public static <T> RestResult<T> ok() {
		RestResultBuilder builder = new RestResultBuilder();
		return builder.ok();
	}
	
	public static <T> RestResult<T> ok(T body) {
		RestResultBuilder builder = new RestResultBuilder();
		return builder.ok(body);
	}
	public static <T> RestResult<T> failure(int status ,String message) {
		RestResultBuilder builder = new RestResultBuilder();
		return builder.failure(status,message,null);
	}
	
	public static <T> RestResult<T> failure(int status ,String message,T body) {
		RestResultBuilder builder = new RestResultBuilder();
		return builder.failure(status,message,body);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
	}
	
	public static class RestResultBuilder{
		
		public <T> RestResult<T> ok() {
			return ok(null);
		}

		public <T> RestResult<T> ok(T body) {
			return new RestResult<T>(GlobalCode.SUCCESS.getStatus(),GlobalCode.SUCCESS.getMessage(),body);
		}
		
		public <T> RestResult<T> failure(int status ,String message,T body) {
			return new RestResult<T>(status,message,body);
		}
	}
}
