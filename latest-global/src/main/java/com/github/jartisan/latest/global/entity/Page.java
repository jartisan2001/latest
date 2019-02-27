package com.github.jartisan.latest.global.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import org.apache.commons.lang3.builder.ToStringBuilder;

/***
 * 分页
 * @author wjl
 * @date 2017/10/16
 */
public class Page implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@DecimalMin(value="0",message="页码为空!")
	private int page;
	@DecimalMin(value="1",message="一页最小为1!")
	@DecimalMax(value="100",message="一页最大为100!")
	private int size=12;
	
	private Map<String,String> orderBy = new HashMap<>();
	
	
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	
	public Map<String, String> getOrderBy() {
		return orderBy;
	}
	
	
	public void setOrderBy(Map<String, String> orderBy) {
		this.orderBy = orderBy;
	}
	
	public void putOrderBy(String key ,String value) {
		orderBy.put(key, value);
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	
}
