package com.github.jartisan.latest.dao.sonatype.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/***
 * FavoriteLog
 * @author jalen
 *
 */

public class FavoriteLog implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * favoritId
	 */
	private Integer favoriteId;
	/**
	 * 当前版本
	 */
	private String version;

	/**
	 * star
	 */
	private Integer starCount;

	/**
	 * watcher
	 */
	private Integer watcherCount;
	/**
	 * fork
	 */
	private Integer forkCount;

	/***
	 * 创建时间
	 */
	private Date createdDate = new Date();

	public FavoriteLog() {
	}
	
	public FavoriteLog(Integer favoriteId, String version, Integer starCount, Integer watcherCount, Integer forkCount) {
		super();
		this.favoriteId = favoriteId;
		this.version = version;
		this.starCount = starCount;
		this.watcherCount = watcherCount;
		this.forkCount = forkCount;
	}
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getFavoriteId() {
		return favoriteId;
	}


	public void setFavoriteId(Integer favoriteId) {
		this.favoriteId = favoriteId;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public Integer getStarCount() {
		return starCount;
	}


	public void setStarCount(Integer starCount) {
		this.starCount = starCount;
	}


	public Integer getWatcherCount() {
		return watcherCount;
	}


	public void setWatcherCount(Integer watcherCount) {
		this.watcherCount = watcherCount;
	}


	public Integer getForkCount() {
		return forkCount;
	}


	public void setForkCount(Integer forkCount) {
		this.forkCount = forkCount;
	}


	public Date getCreatedDate() {
		if (this.createdDate != null) {
			return new Date(this.createdDate.getTime());
		} else {
			return null;
		}
	}

	public void setCreatedDate(Date createdDate) {
		if (createdDate != null) {
			this.createdDate = (Date) createdDate.clone();
		} else {
			this.createdDate = null;
		}
	}

	


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
