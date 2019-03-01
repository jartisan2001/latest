package com.github.jartisan.latest.dao.sonatype.query;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Transient;

/***
 * Latest
 * 
 * @author jalen
 *
 */
public class LatestQuery {
	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * favoritId
	 */
	private Integer favoriteId;

	/**
	 * 名称
	 */
	private String name;
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
	
	/**
     * github_api地址
     */
    private String githubApi;
    
    
    /**
     * fullName
     */
    @Transient
	private String fullName;
	

	/***
	 * 创建时间
	 */
	private Date createdDate = new Date();
	
	
	public LatestQuery() {
		super();
	}


	public LatestQuery(Integer id, Integer favoriteId, String name, String version, Integer starCount, Integer watcherCount,
			Integer forkCount) {
		super();
		this.id = id;
		this.favoriteId = favoriteId;
		this.name = name;
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


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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


	
	public String getGithubApi() {
		return githubApi;
	}


	public void setGithubApi(String githubApi) {
		this.githubApi = githubApi;
	}


	
	 /**
     * 项目全名
     * @return 项目全名
     * @throws MalformedURLException 
     */
    public String getFullName() throws MalformedURLException {
    	URL url = new URL(this.githubApi);
        return StringUtils.remove(url.getPath(), "/repos/");
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
